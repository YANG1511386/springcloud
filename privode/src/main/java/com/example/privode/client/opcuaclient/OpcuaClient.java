package com.example.privode.client.opcuaclient;
import com.alibaba.fastjson.JSON;
import com.example.privode.util.Changetype;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.milo.opcua.sdk.client.OpcUaClient;
import org.eclipse.milo.opcua.sdk.client.api.config.OpcUaClientConfig;
import org.eclipse.milo.opcua.sdk.client.api.identity.UsernameProvider;
import org.eclipse.milo.opcua.sdk.client.nodes.UaNode;
import org.eclipse.milo.opcua.stack.client.DiscoveryClient;
import org.eclipse.milo.opcua.stack.core.AttributeId;
import org.eclipse.milo.opcua.stack.core.Identifiers;
import org.eclipse.milo.opcua.stack.core.UaException;
import org.eclipse.milo.opcua.stack.core.types.builtin.*;
import org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.UInteger;
import org.eclipse.milo.opcua.stack.core.types.enumerated.MonitoringMode;
import org.eclipse.milo.opcua.stack.core.types.enumerated.TimestampsToReturn;
import org.eclipse.milo.opcua.stack.core.types.structured.EndpointDescription;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoredItemCreateRequest;
import org.eclipse.milo.opcua.stack.core.types.structured.MonitoringParameters;
import org.eclipse.milo.opcua.stack.core.types.structured.ReadValueId;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import static com.example.privode.mywebsocket.NoticeWebsocket.sendMessage;
import static org.eclipse.milo.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

@Component
@Slf4j
public class OpcuaClient {
//    ObjectMapper objectMapper = new ObjectMapper();
   Map<String, Object> map = new HashMap<>();
  private  OpcUaClient client ;

  //连接opcua账号，密码
  final static String USERNAME = "myopcua";
  final static String PASSWORD = "123456yangyongle";
  //客户端名字
  final static String CLIENTNAME = "OPC UA Client Example";
  //客户端地址
  final static String CLIENTADRESS = "urn:localhost:OPCUAClient";

    /**
     * 初始化客户端对象
     * @param serverUrl
     * @throws Exception
     */
    public  void createClientdemo(String serverUrl) throws Exception{
        try {
            // 发现可用的终端点
            List<EndpointDescription> endpoints = DiscoveryClient.getEndpoints(serverUrl).get();
            // 选择终端点
            EndpointDescription endpoint = selectEndpoint(endpoints);
            if (endpoint != null) {
                // 创建OpcUaClient实例并连接到服务器
                client = createClient(endpoint,USERNAME,PASSWORD);
                client.connect().get();
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    };

    /**
     * 挑选终端节点
     * @param endpoints
     * @return
     */
    public static EndpointDescription selectEndpoint(List<EndpointDescription> endpoints) {
        for(EndpointDescription endpointDescription:endpoints){
            String string = endpointDescription.toString();
        }
        // 在此示例中，我们简单地选择第一个终端点。
        // 您可以根据需要实现更复杂的逻辑来选择合适的终端点。

        return endpoints.stream().findFirst().orElse(null);
    }

    /**
     * 关闭客户端方法
     */
    public void disconnect(){
        client.disconnect();
    }


    /**
     * 创造客户端
     * @param endpoint
     * @return
     * @throws UaException
     */
    private static OpcUaClient createClient(EndpointDescription endpoint,String username,String password) throws UaException {
        OpcUaClientConfig config = OpcUaClientConfig.builder()
                .setEndpoint(endpoint) //终端点选择
                .setApplicationName(LocalizedText.english(CLIENTNAME))//客户端名字
                .setApplicationUri(CLIENTADRESS)//客户端地址
                .setCertificate(null)//设置证书
                .setKeyPair(null)//设置密钥
                .setRequestTimeout(uint(5000))//设置连接超时
                .setIdentityProvider(new UsernameProvider(username,password))//账户名密码登录，匿名登录则填null
                .build()
                ;
        return OpcUaClient.create(config);
    };


    /**
     * 遍历树形节点
     *
     * @param uaNode 节点
     * @throws Exception
     */
    public  void browseNode( UaNode uaNode) throws Exception {
        List<? extends UaNode> nodes;
        if (uaNode == null) {
            nodes = client.getAddressSpace().browseNodes(Identifiers.ObjectsFolder);
        } else {
            nodes = client.getAddressSpace().browseNodes(uaNode);
        }
        for (UaNode nd : nodes) {
            //排除系统行性节点，这些系统性节点名称一般都是以"_"开头
            if (Objects.requireNonNull(nd.getBrowseName().getName()).contains("_")) {
                continue;
            }
            log.info("Node= " + nd.getBrowseName().getName()+nd.getNodeId());
            browseNode(nd);
        }
    }

    /**
     * 同步读取节点数据
     *
     * @throws Exception
     */
    public  String readNode(String id) throws Exception {
        String identifier = id;
        //节点
        NodeId nodeId = new NodeId(2, identifier);
        //读取节点数据
        DataValue value = client.readValue(0.0, TimestampsToReturn.Neither, nodeId).get();
//        //标识符
        String s = id+ ": " + String.valueOf(value.getValue().getValue());
        return s;
    }

    /**
     * 同步批量读取节点数据
     *
     * @throws Exception
     */
    public  List<String> readListNode(List<String> idlist) throws Exception {
        List<String> list = new ArrayList<String>();
         List<NodeId> nodeIds = new ArrayList<NodeId>();
        int i = 0;
        //节点
        for(String id:idlist){
        NodeId nodeId = new NodeId(2, id);
        nodeIds.add(nodeId);
        }
        //读取节点数据
        List<DataValue> value = client.readValues(0.0, TimestampsToReturn.Neither, nodeIds).get();
        for(DataValue dataValue:value) {
//        //标识符
            String s = idlist.get(i) + ": " + dataValue.getValue().getValue();
            i++;
            list.add(s);
        }
        return list;
    }

    /**
     * 异步读取
     */
    public  String asyreadNode(String id) {
        String identifier = id;
        //节点
        NodeId nodeId = new NodeId(2, identifier);
        // 异步读取节点数据
        CompletableFuture<DataValue> dataValueCompletableFuture = client.readValue(0.0, TimestampsToReturn.Neither, nodeId);
        // 处理异步结果
        dataValueCompletableFuture.thenAccept(dataValue -> {
            Variant value1 = dataValue.getValue();
            String string = id+":"+value1.getValue().toString();
            map.put("type","readandwriter");
            map.put("value",string);
            String jsonString = JSON.toJSONString(map);
            sendMessage(jsonString);
//            try {
//                String s = objectMapper.writeValueAsString(map);
//                sendMessage(s);
//            } catch (JsonProcessingException e) {
//                throw new RuntimeException(e);
//            }
            log.info(id+string);
        }).join();
            return "调用成功";
    }



    /**
     * 异步批量读取
     * @throws Exception
     */
    public  void asyreadlistNode(List<String> idlist) throws Exception {
        List<NodeId> nodeIds = new ArrayList<NodeId>();

        //节点
        for(String id:idlist){
            NodeId nodeId = new NodeId(2, id);
            nodeIds.add(nodeId);
        }
        // 异步读取节点数据
        //模拟读取时间
        CompletableFuture<List<DataValue>> listCompletableFuture = client.readValues(0.0, TimestampsToReturn.Neither, nodeIds);
        // 处理异步结果
            listCompletableFuture.thenAccept(dataValues -> {
                List<String> list = new ArrayList<>();
                int i = 0;
                for(DataValue dataValue:dataValues) {
                    Variant value1 = dataValue.getValue();
                    String string = idlist.get(i)+":"+value1.getValue().toString();
                    list.add(string);
                    i++;
                }
                sendMessage(list.toString());
            }).join();
    }




    /**
     * 同步写入节点数据
     *
     * @throws Exception
     */
    public String writeNodeValue(String id,String value,String paramtype) throws Exception {
        //节点
        NodeId nodeId = new NodeId(2, id);
        Object data = Changetype.createData(paramtype, value);
        //创建数据对象,此处的数据对象一定要定义类型，不然会出现类型错误，导致无法写入
        DataValue nowValue = new DataValue(new Variant(data), null, null);
        //写入节点数据
        StatusCode statusCode = client.writeValue(nodeId, nowValue).join();
       return  "结果：" + statusCode.isGood();
    }



    /**
     * 同步批量写入节点数据
     *
     * @throws Exception
     */
    public List<Long> writelistNodeValue(List<String> idlist,List<Object> valuelist,String dtatype) throws Exception {
        List<Long> list = new ArrayList<>();
        List<NodeId> nodeIds = new ArrayList<NodeId>();
        //节点
        for(String id:idlist){
            NodeId nodeId = new NodeId(2, id);
            nodeIds.add(nodeId);
        }
        List<DataValue> dataValues = new ArrayList<DataValue>();
        for(Object j:valuelist){
            Object data = Changetype.createData(dtatype, j.toString());
            //创建数据对象,此处的数据对象一定要定义类型，不然会出现类型错误，导致无法写入
            DataValue nowValue = new DataValue(new Variant(data), null, null);
            dataValues.add(nowValue);
        }
        //写入节点数据
        List<StatusCode> join = client.writeValues(nodeIds, dataValues).join();
        for (StatusCode statusCode:join){
            list.add(statusCode.getValue());
        }

        return  list;
    }


    /**
     * 异步写入节点数据
     *
     * @throws Exception
     */
    public  void asywriteNodeValue(String id ,Object i) throws Exception {
        //节点
        NodeId nodeId = new NodeId(2, id);
        AtomicReference<String> s = new AtomicReference<>("");
        //创建数据对象,此处的数据对象一定要定义类型，不然会出现类型错误，导致无法写入
        DataValue nowValue = new DataValue(new Variant(i), null, null);
        //写入节点数据
        //模拟写入时间
        CompletableFuture<StatusCode> statusCodeCompletableFuture = client.writeValue(nodeId, nowValue);
        // 处理异步结果
        statusCodeCompletableFuture.thenRun(() -> {
            map.put("type","readandwriter");
            map.put("value","异步写入成功");
//            try {
//                String jsonString = objectMapper.writeValueAsString(map);
//                sendMessage(jsonString);
//            } catch (JsonProcessingException e) {
//                throw new RuntimeException(e);
//            }
            String jsonString = JSON.toJSONString(map);
            sendMessage(jsonString);
        }).join();
    }


    /**
     * 异步批量写入节点数据
     *
     * @throws Exception
     */
    public  void asywriteListNodeValue(List<String> idlist,List<Object> valuelist,String dtatype) throws Exception {
        List<Long> list = new ArrayList<>();
        List<NodeId> nodeIds = new ArrayList<NodeId>();
        //节点
        for(String id:idlist){
            NodeId nodeId = new NodeId(2, id);
            nodeIds.add(nodeId);
        }
        List<DataValue> dataValues = new ArrayList<DataValue>();
        for(Object j:valuelist){
            Object data = Changetype.createData(dtatype, j.toString());
            //创建数据对象,此处的数据对象一定要定义类型，不然会出现类型错误，导致无法写入
            DataValue nowValue = new DataValue(new Variant(data), null, null);
            dataValues.add(nowValue);
        }
        //写入节点数据
        CompletableFuture<List<StatusCode>> listCompletableFuture = client.writeValues(nodeIds, dataValues);
        // 处理异步结果
        listCompletableFuture.thenRun(() -> {
            map.put("type","readandwriter");
            map.put("value","批量异步写入成功");
//            try {
//                String jsonString = objectMapper.writeValueAsString(map);
//                sendMessage(jsonString);
//            } catch (JsonProcessingException e) {
//                throw new RuntimeException(e);
//            }
            String jsonString = JSON.toJSONString(map);
            sendMessage(jsonString);
        }).join();
    }



    /**
     * 订阅
     *
     * @throws Exception
     */
    public  void subscribe(String id) throws Exception {
        List<MonitoredItemCreateRequest> requests = new ArrayList<>();

        //创建发布间隔1000ms的订阅对象
        client
                .getSubscriptionManager()
                .createSubscription(1000.0)
                .thenAccept(t -> {
                    //创建监控的参数
                    MonitoringParameters parameters = new MonitoringParameters(UInteger.valueOf(new AtomicInteger(1).getAndIncrement()), 1000.0, null, UInteger.valueOf(10), true);
                        //节点
                        NodeId nodeId = new NodeId(2, id);
                        ReadValueId readValueId = new ReadValueId(nodeId, AttributeId.Value.uid(), null, null);
                        //创建监控项请求
                        //该请求最后用于创建订阅。
                        MonitoredItemCreateRequest request = new MonitoredItemCreateRequest(readValueId, MonitoringMode.Reporting, parameters);
                        requests.add(request);
                        // 创建监控项，并且注册变量值改变时候的回调函数。
                        t.createMonitoredItems(
                                TimestampsToReturn.Both,
                                requests,
                                (item, ex) -> item.setValueConsumer((it, val) -> {
                                    log.info("nodeid :" +  it.getReadValueId().getNodeId() + "value :" + val.getValue().getValue());
                                    map.put("type","subscribe");
                                    map.put("value","nodeid :" + it.getReadValueId().getNodeId() + "value :" + val.getValue().getValue());
//                                    try {
//                                        String jsonString = objectMapper.writeValueAsString(map);
//                                        sendMessage(jsonString);
//                                    } catch (JsonProcessingException e) {
//                                        throw new RuntimeException(e);
//                                    }
                                    String jsonString = JSON.toJSONString(map);
                                    sendMessage(jsonString);
                                })
                        );
                }).get();
    }
}


