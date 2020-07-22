package com.logibar.knx

import org.junit.jupiter.api.Test
import tuwien.auto.calimero.DeviceDescriptor
import tuwien.auto.calimero.IndividualAddress
import tuwien.auto.calimero.knxnetip.KNXnetIPConnection
import tuwien.auto.calimero.link.KNXNetworkLinkIP
import tuwien.auto.calimero.link.medium.TPSettings
import tuwien.auto.calimero.mgmt.ManagementClientImpl
import java.net.InetSocketAddress

class JustConnect {
    @Test
    fun connect(){
        val port=KNXnetIPConnection.DEFAULT_PORT
        val medium=TPSettings.TP1
        val lineAddress= IndividualAddress("1.1.10")
        val localHost="172.19.1.51"
        val gateway="172.19.4.10"
        val networkLink= KNXNetworkLinkIP.newTunnelingLink(InetSocketAddress(localHost, 0), InetSocketAddress(gateway, 3671), false, TPSettings.TP1)
        val client= ManagementClientImpl(networkLink)
        val dest=client.createDestination(lineAddress, true)
//        val deviceDescriptor=client.readDeviceDesc(dest,0)

        try{
        val x=client.readMemory(dest, 16384, 100)

        // check for BCU1/BCU2 first, which don't have interface objects
//        if (deviceDescriptor != null) {
//            println(deviceDescriptor.toString())
            println(x.toString())

//            if (deviceDescriptor == DeviceDescriptor.DD0.TYPE_1013.) readPL110Bcu1() else if (dd === DD0.TYPE_0010 || dd === DD0.TYPE_0011 || dd === DD0.TYPE_0012) readTP1Bcu1() else if (dd === DD0.TYPE_0020 || dd === DD0.TYPE_0021 || dd === DD0.TYPE_0025) readTP1Bcu2() else {
//                findInterfaceObjects()
//            }
//        } else {
//            findInterfaceObjects()
        //}
    }
        catch(e:Throwable){
            println(e.message)
        }finally {
            client.detach()
        }
    }

}
