package com.example.privode.server.modbusmaster;



import com.ghgande.j2mod.modbus.procimg.Register;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ModbusMaster {
    //读取数据中去除头部部分数据
    private final static int removenum = 2;
    //以8个数据为一组作为托盘码
    private final static int groupnum = 8;
    ModbusMasterTool masterTool = new ModbusMasterTool("127.0.0.1", 502);
    Register[] registers;
    Register[] result;
    int numberOfGroups;
    Register[][] groupedRegisters;
    String[] strings;
    public String[] redecode(int startdata,int count) throws Exception {

        masterTool.connect();
        try {
            registers = masterTool.readMultipleRegisters(1, startdata, count);
            // 去除前两个元素
            int newSize = registers.length - removenum;
            result = new Register[newSize];
            for (int i = removenum; i < registers.length; i++) {
                result[i - removenum] = registers[i];
            }
            // 按照每8个元素为一组进行处理
            numberOfGroups = (int) Math.ceil((double) newSize / groupnum);
            groupedRegisters = new Register[numberOfGroups][groupnum];
            strings = new String[numberOfGroups];
            for (int i = 0; i < numberOfGroups; i++) {
                for (int j = 0; j < groupnum && (i * groupnum + j) < newSize; j++) {
                    groupedRegisters[i][j] = result[i * groupnum + j];
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        };
        for(int j=0;j<numberOfGroups;j++) {
            StringBuilder code = new StringBuilder();
            for (Register reg : groupedRegisters[j]) {
                code.append(intToAscii(reg.getValue()));
            }
//            int x =j+1;
//            log.info("di"+x+"de===>"+code + "==========================================================");
            strings[j]=code.toString();
        }
        masterTool.disconnect();
        return strings;
    };
    //将读取的整数转换成字符
    public static String intToAscii(int value) {
        // 分解整数为两个字节
        char highByte = (char) ((value >> 8) & 0xFF);  // 获取高8位
        char lowByte = (char) (value & 0xFF);         // 获取低8位
        // 将两个字节转换为其对应的ASCII字符并拼接
        return "" + highByte + lowByte;
    }
}
