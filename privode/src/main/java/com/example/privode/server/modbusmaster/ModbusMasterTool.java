package com.example.privode.server.modbusmaster;
import com.ghgande.j2mod.modbus.ModbusException;
import com.ghgande.j2mod.modbus.facade.ModbusTCPMaster;
import com.ghgande.j2mod.modbus.procimg.InputRegister;
import com.ghgande.j2mod.modbus.procimg.Register;
import com.ghgande.j2mod.modbus.procimg.SimpleRegister;
import com.ghgande.j2mod.modbus.util.BitVector;

import java.util.List;

public class ModbusMasterTool {

    private String ipAddress;
    private int port;
    private ModbusTCPMaster master;

    public ModbusMasterTool(String ipAddress, int port) {
        this.ipAddress = ipAddress;
        this.port = port;
        master = new ModbusTCPMaster(ipAddress, port);
    }

    public void connect() throws Exception {
        if (!master.isConnected()) {
            master.connect();
        }
    }

    public void disconnect() {
        master.disconnect();
    }

    /**
     * 读线圈 操作01
     * @param unitId
     * @param ref
     * @param count
     * @return
     * @throws ModbusException
     */
    public BitVector readCoils(int unitId, int ref, int count) throws ModbusException {
        return  master.readCoils(unitId,ref,count);
    }

    /**
     * 写入单个线圈
     * @param unitId
     * @param ref
     * @param state 线圈状态
     * @return
     * @throws ModbusException
     */
    public boolean writeCoil(int unitId, int ref, boolean state) throws ModbusException {
        return  master.writeCoil(unitId,ref,state);
    }

    /**
     * 写入多个线圈值
     * @param unitId
     * @param ref
     * @param coils
     * @throws ModbusException
     */
    public void writeMultipleCoils(int unitId, int ref, BitVector coils) throws ModbusException {
        master.writeMultipleCoils(unitId,ref,coils);
    }


    /**
     * 读取多个离散值
     * @param ref
     * @param count
     * @throws ModbusException
     */
    public BitVector readInputDiscretes(int ref, int count) throws ModbusException {
        return master.readInputDiscretes(ref, count);
    }

    /**
     * 读取输入寄存器 操作码04
     * @param ref
     * @param count
     * @return
     * @throws ModbusException
     */
    public InputRegister[] readInputRegisters(int ref, int count) throws ModbusException {
        return master.readInputRegisters(ref,count);
    }

    /**
     读取寄存器 操作码03
     unitId: 这是设备的单元标识符。它用于标识Modbus网络上的特定设备。在传统的串行Modbus中，这被称为设备或站点的地址。在Modbus TCP中，每个设备可以有多个单元ID，因为TCP/IP地址已经定义了设备的位置。但是，通常Modbus TCP设备只有一个单元ID，并且常常是1。

     ref: 这是引用的起始地址，代表你想要开始读取的第一个寄存器的地址。

     count: 这是要读取的寄存器的数量。从起始地址ref开始，你想要读取的寄存器的数目。

     例如，如果你调用readMultipleRegisters(1, 30001, 10)，这意味着你想从单元ID为1的设备读取从地址30001开始的10个连续的寄存器。
     */
    public Register[] readMultipleRegisters(int unitId, int ref, int count) throws Exception {
        return master.readMultipleRegisters(unitId, ref, count);
    }


    /**
     * 写单个寄存器 操作码06
     unitId: 单元标识符。这是Modbus设备的地址或ID。在串行通讯（如Modbus RTU）中，这是设备或站点的地址。在Modbus TCP中，由于TCP/IP地址已经定义了设备的位置，这个ID更像是子设备或单元的标识符。

     ref: 这是你想要写入的保持寄存器的地址。请注意，这个地址可能是从0开始的，或可能基于Modbus文档中的原始地址（例如，40001可能变为地址0）。

     register: 这是一个Register对象，表示你要写入的数据。Register对象通常会提供一个包装你希望写入的数值的方法。在j2mod库中，SimpleRegister是Register接口的一种实现，你可以使用它来包装一个16位的整数值。
     */
    public int writeSingleRegister(int unitId, int ref, int register) throws ModbusException {
        Register rst = new SimpleRegister(register);
        return master.writeSingleRegister(unitId,ref,rst);
    }

    /**
     * 写入多个寄存器
     * @param ref
     * @param registerslist
     * @return
     * @throws ModbusException
     */
    public int writeMultipleRegisters( int ref, List<Integer> registerslist) throws ModbusException {
        Register[] registers = new Register[registerslist.size()];
        for(int i=0;i<registerslist.size();i++){
            registers[i] = new SimpleRegister(registerslist.get(i));
        }
        return master.writeMultipleRegisters(ref,registers);
    }


}
