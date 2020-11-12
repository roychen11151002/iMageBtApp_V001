package com.example.imagebtapp_v001b001

import android.net.Uri
import android.os.Handler

class BtDevMsg(var btDevNo: Int = 0, var btGroup: Int = 0) {
companion object {

    fun bdaddrTranslate(msg: BtDevMsg, offset: Int) = String.format("%02X:%02X:%02X:%02X:%02X:%02X", msg.btCmd[offset + 4], msg.btCmd[offset + 5], msg.btCmd[offset + 3], msg.btCmd[offset], msg.btCmd[offset + 1], msg.btCmd[offset + 2])
}

    var btCmd = ByteArray(256 + 7)
}

class BtDevUnit {
    companion object {
        // lateinit var resolver: ContentResolver
        var sppStateCon: Byte = 1
        var PairState = 0
        val MaxBtDev = 2
        var previousItem = 256
        var isReconnect = true
        var staUpdateInterval = 600
        val BtPermissionReqCode = 1
        val BtActionReqCode = 66
        var BtList = ArrayList<String>()
        var maxTalkNo = 0
        var maxAgNo = 0
        var deviceNo = 6
        val featureMaxTalkNo = 4
        val featuerMaxAgNo = 0x10
        val featureBdaddrFilter = "C4:FF:BC:00:00:00"
        val featuerM6Src = 0xa222
        val ledPwrM6Src = 0x03ff
        val ledMfbM6Src = 0x03ff
        val ledBcbM6Src =0x03ff
        val ledRevM6Src = 0x03ff
        val featuerM6Ag = 0xa220
        val ledPwrM6Ag = 0x03ff
        val ledMfbM6Ag = 0x03ff
        val ledBcbM6Ag =0x03ff
        val ledRevM6Ag = 0x03ff
        val featuerDg = 0xa221
        val ledPwrDg = 0x03ff
        val ledMfbDg = 0x03ff
        val ledBcbDg =0x03ff
        val ledRevDg = 0x03ff
        val featuerVc = 0xa220
        val ledPwrVc = 0x03ff
        val ledMfbVc = 0x03ff
        val ledBcbVc =0x03ff
        val ledRevVc = 0x03ff
        val featuerA6 = 0x8002
        val ledPwrA6 = 0x03ff
        val ledMfbA6 = 0x03ff
        val ledBcbA6 =0x03ff
        val ledRevA6 = 0x03ff
        val featuerA7 = 0x8003
        val ledPwrA7 = 0x03ff
        val ledMfbA7 = 0x03ff
        val ledBcbA7 =0x03ff
        val ledRevA7 = 0x03ff
        val paraDataA6 = arrayOf(
            arrayOf("227c 227d 00df e188 0466 f70c 6666 6603 6666 3333 ff00 ffff 0000 7ee0 00f9 0004 5b2d 0800 0000 0004 197f 9999 ffff 0000 000f 0002 0000 80c9 0000 0000 0000 0000 0000 0018 3300 3333 0003 0803 000b 80c9 3f30 0c00 cccc 0000 0100 1900 8a13 0000 0000 0006 7f00 ffff 0199",
                "227d 0000 e600 e000 0001 0109 47ae 0100 47ae"),
            arrayOf("227c 227d 00df e188 0066 f60c 6666 6603 6666 3333 ff00 ffff 0000 7ee0 00f9 0004 5b2d 0800 0000 0004 197f 9999 ffff 0000 000f 0002 0000 80c9 0000 0000 0000 0000 0000 0018 3300 3333 0003 0803 000b 80c9 3f30 0c00 cccc 0000 0100 1900 8a13 0000 0000 0006 7f00 ffff 0199",
                "227d 0000 e600 e000 0001 0109 47ae 0100 47ae"),
            arrayOf("227c 227d 00df e188 0466 f70c 6666 6603 6666 3333 ff00 ffff 0000 7ee0 00f9 0004 5b2d 0800 0000 0004 197f 9999 ffff 0000 000f 0002 0000 00cc 0000 0000 0000 0000 0000 0018 3300 3333 0003 0803 000b 00cc 3f30 0c00 cccc 0000 0100 1900 8a13 0000 0000 0006 7f00 ffff 0199",
                "227d 0000 e600 e000 0001 0109 47ae 0100 47ae"),
            arrayOf("227c 227d 00de e188 0066 f60c 6666 6603 6666 3333 ff00 ffff 0000 7ee0 00f9 0004 5b2d 0800 0000 0004 197f 9999 ffff 0000 000f 0002 0000 80c9 0000 0000 0000 0000 0000 0018 3300 3333 0003 0803 000b 80c9 3f30 0c00 cccc 0000 0300 0b19 3f30 8a13 0000 0000 0004 7f00 ffff",
                "227d 0000 de00 80e0 0000 0199 0109 0101 47ae 47ae"),
            arrayOf("227c 227d 00df e188 0466 f70c 6666 6603 6666 3333 ff00 ffff 0000 7ee0 00f9 0004 5b2d 0800 0000 0004 197f 9999 ffff 0000 000f 0002 0000 80c9 0000 0000 0000 0000 0000 0018 3300 3333 0003 0803 000b 80c9 3f30 0c00 cccc 0000 0100 1900 8a13 0000 0000 0006 7f00 ffff 0199",
                "227d 0000 e600 e000 0001 0109 47ae 0100 47ae"),
            arrayOf("227c 227d 00df e188 0466 f70c 6666 6603 6666 3333 ff00 ffff 0000 7ee0 00f9 0004 5b2d 0800 0000 0004 197f 9999 ffff 0000 000f 0002 0000 80c9 0000 0000 0000 0000 0000 0018 3300 3333 0003 0803 000b 80c9 3f30 0c00 cccc 0000 0100 1900 8a13 0000 0000 0006 7f00 ffff 0199",
                "227d 0000 e600 e000 0001 0109 47ae 0100 47ae")
        )
        val paraDataA7 = arrayOf(
            arrayOf("227c 227d 00df e188 0466 f70c 6666 6603 6666 3333 ff00 ffff 0000 7ee0 00f9 0004 5b2d 0800 0000 0004 197f 9999 ffff 0000 000f 0002 0000 00cd 0000 0000 0000 0000 0000 0018 3300 3333 0003 0803 000b 00cd 3f30 0c00 cccc 0000 0100 1900 8a13 0000 0000 0006 7f00 ffff 0199",
                "227d 0000 e600 e000 0001 0109 47ae 0100 47ae"),
            arrayOf("227c 227d 00df e188 0066 f60c 6666 6603 6666 3333 ff00 ffff 0000 7ee0 00f9 0004 5b2d 0800 0000 0004 197f 9999 ffff 0000 000f 0002 0000 00cd 0000 0000 0000 0000 0000 0018 3300 3333 0003 0803 000b 00cd 3f30 0c00 cccc 0000 0100 1900 8a13 0000 0000 0006 7f00 ffff 0199",
                "227d 0000 e600 e000 0001 0109 47ae 0100 47ae"),
            arrayOf("227c 227d 00df e188 0466 f70c 6666 6603 6666 3333 ff00 ffff 0000 7ee0 00f9 0004 5b2d 0800 0000 0004 197f 9999 ffff 0000 000f 0002 0000 00c9 0000 0000 0000 0000 0000 0018 3300 3333 0003 0803 000b 00c9 3f30 0c00 cccc 0000 0100 1900 8a13 0000 0000 0006 7f00 ffff 0199",
                "227d 0000 e600 e000 0001 0109 47ae 0100 47ae"),
            arrayOf("227c 227d 00de e188 0066 f60c 6666 6603 6666 3333 ff00 ffff 0000 7ee0 00f9 0004 5b2d 0800 0000 0004 197f 9999 ffff 0000 000f 0002 0000 00cd 0000 0000 0000 0000 0000 0018 3300 3333 0003 0803 000b 00cd 3f30 0c00 cccc 0000 0300 0b19 3f30 8a13 0000 0000 0004 7f00 ffff",
                "227d 0000 de00 80e0 0000 0199 0109 0101 47ae 47ae"),
            arrayOf("227c 227d 00df e188 0466 f70c 6666 6603 6666 3333 ffe8 ffff 0000 7ee0 00f9 0004 5b2d 0800 0000 0004 197f 9999 ffff 0000 000f 0002 0000 00cd 0000 0000 0000 0000 0000 0018 3300 3333 0003 0803 000b 00cd 3f30 0c00 cccc 0000 0100 1900 8a13 0000 0000 0006 7f00 ffff 0199",
                "227d 0000 e600 8000 0000 0109"),
            arrayOf("227c 227d 00df e188 0466 f70c 6666 6603 6666 3333 ffe8 ffff 0000 7ee0 00f9 0004 5b2d 0800 0000 0004 197f 9999 ffff 0000 000f 0002 0000 00cd 0000 0000 0000 0000 0000 0018 3300 3333 0003 0803 000b 00cd 3f30 0c00 cccc 0000 0100 1900 8a13 0000 0000 0006 7f00 ffff 0199",
                "227d 0000 e600 8000 0000 0109")
        )

        fun getBtDevId(id: Byte) =
            when(id) {
                0x30.toByte() -> 0
                0x00.toByte() -> 1
                0x08.toByte() -> 2
                0x10.toByte() -> 3
                0x18.toByte() -> 4
                0x20.toByte() -> 5
                0x28.toByte() -> 6
                CmdId.CMD_DEV_HOST.value -> 0x80
                else -> 0xff
            }
    }

    var imgIconUri = Uri.parse("")
    var imgIconFlash = true
    var bdaddr = "00:00:00:00:00:00"
    var bdaddrPair = "00:00:00:00:00:00"
    var bdaddrFilterHfp = "00:00:00:00:00:00"
    var bdaddrFilterAg = "00:00:00:00:00:00"
    var ledLightHfp = arrayOf(0, 0, 0, 0)
    var ledLightAg = arrayOf(0, 0, 0, 0)
    var verFirmwareHfp = ""
    var verFirmwareAg = ""
    var nameAlias = "alias name"
    var nameLocalHfp = ""
    var nameLocalAg = ""
    var featureHfp = 0
    var featureAg = 0
    var featureMode = 0x01000000
    var stateCon = 0
    var stateDial: Int = 0
    var stateExtra: Int = 0
    var rssi: Int = 0
    var volSpkrHfp: Int = 15
    var volMicHfp: Int = 15
    var muteSpkr: Boolean = false
    var muteMic: Boolean = false
    var batInd: Boolean = false
    var devDfu: Boolean = false
    var volSpkrAg: Int = 15
    var volMicAg: Int = 15
    var batLevel: Int = 0
    var modeSrcWireHfpMicVol = 15
    var modeSrcWireHfpSpkrVol = 15
    var modeSrcWireAvSpkrVol = 15
    var modeSrcUsbHfpMicVol = 15
    var modeSrcUsbHfpSpkrVol = 15
    var modeSrcUsbAvSpkrVol = 15
    var modeSrcBtHfpMicVol = 15
    var modeSrcBtHfpSpkrVol = 15
    var modeSrcBtAvSpkrVol = 15
    var modeSrcVcsHfpMicVol = 15
    var modeSrcVcsHfpSpkrVol = 15
    var modeSrcVcsAvSpkrVol = 15
    var modeSrcSpkrDecade = 0
    var modeAgWireHfpMicVol = 15
    var modeAgWireHfpSpkrVol = 15
    var modeAgWireAvSpkrVol = 15
    var modeAgUsbHfpMicVol = 15
    var modeAgUsbHfpSpkrVol = 15
    var modeAgUsbAvSpkrVol = 15
    var modeAgBtHfpMicVol = 15
    var modeAgBtHfpSpkrVol = 15
    var modeAgBtAvSpkrVol = 15
    var modeAgVcsHfpMicVol = 15
    var modeAgVcsHfpSpkrVol = 15
    var modeAgVcsAvSpkrVol = 15
    var txPowerHfp = 20
    var txPowerAg = 20
}

enum class CmdId(val value: Byte) {
    CMD_HEAD_FF(0xff.toByte()),
    CMD_HEAD_55(0x55.toByte()),
    CMD_DEV_HOST(0x80.toByte()),
    CMD_DEV_SRC(0x30.toByte()),
    CMD_DEV_AG(0x00.toByte()),
    CMD_DEV_AG_ALL(0x38.toByte()),
    CMD_DEV_HFP_ALL(0x38.toByte()),
    SET_HFP_PAIR_REQ(0x02.toByte()),
    SET_HFP_PAIR_RSP(0x03.toByte()),
    SET_AG_VOL_REQ(0x06.toByte()),
    SET_AG_VOL_RSP(0x07.toByte()),
    SET_HFP_VOL_REQ(0x08.toByte()),
    SET_HFP_VOL_RSP(0x09.toByte()),
    SET_HFP_STA_REQ(0x0a.toByte()),
    SET_HFP_STA_RSP(0x0b.toByte()),
    SET_HFP_EXT_STA_REQ(0x0c.toByte()),
    SET_HFP_EXT_STA_RSP(0x0d.toByte()),
    SET_HFP_PSKEY_REQ(0x10.toByte()),
    SET_HFP_PSKEY_RSP(0x11.toByte()),
    SET_AG_PSKEY_REQ(0x12.toByte()),
    SET_AG_PSKEY_RSP(0x13.toByte()),
    SET_HFP_LOCAL_NAME_REQ(0x14.toByte()),
    SET_HFP_LOCAL_NAME_RSP(0x15.toByte()),
    SET_AG_LOCAL_NAME_REQ(0x16.toByte()),
    SET_AG_LOCAL_NAME_RSP(0x17.toByte()),
    SET_HFP_FEATURE_REQ(0x1c.toByte()),
    SET_HFP_FEATURE_RSP(0x1d.toByte()),
    SET_AG_FEATURE_REQ(0x1e.toByte()),
    SET_AG_FEATURE_RSP(0x1f.toByte()),
    SET_HFP_DIAL_REQ(0x20.toByte()),
    SET_HFP_DIAL_RSP(0x21.toByte()),
    SET_DISCOVERY_REQ(0x22.toByte()),
    SET_DISCOVERY_RSP(0x23.toByte()),
    SET_HFP_CTRL_REQ(0x38.toByte()),
    SET_HFP_CTRL_RSP(0x39.toByte()),
    SET_AG_CTRL_REQ(0x3a.toByte()),
    SET_AG_CTRL_RSP(0x3b.toByte()),
    SET_AG_DFU_REQ(0x3c.toByte()),                  // phase out command
    SET_AG_DFU_RSP(0x3d.toByte()),                  // phase out command
    SET_HFP_DFU_REQ(0x3e.toByte()),                 // phase out command
    SET_FHP_DFU_RSP(0x3f.toByte()),                 // phase out command
    SET_INT_SERVICE_REQ(0xe0.toByte()),
    SET_INT_SERVICE_RSP(0xe1.toByte()),
    SET_INT_CON_REQ(0xe2.toByte()),
    SET_INT_CON_RSP(0xe3.toByte()),
    SET_INT_DISCOVERY_REQ(0xe6.toByte()),
    SET_INT_DISCOVERY_RSP(0xe7.toByte()),
    SET_INT_PAIR_REQ(0xe8.toByte()),
    SET_INT_PAIR_RSP(0xe9.toByte()),

    GET_HFP_PAIR_REQ(0x42.toByte()),
    GET_HFP_PAIR_RSP(0x43.toByte()),
    GET_AG_VOL_REQ(0x46.toByte()),
    GET_AG_VOL_RSP(0x47.toByte()),
    GET_HFP_VOL_REQ(0x48.toByte()),
    GET_HFP_VOL_RSP(0x49.toByte()),
    GET_HFP_STA_REQ(0x4a.toByte()),
    GET_HFP_STA_RSP(0x4b.toByte()),
    GET_HFP_EXT_STA_REQ(0x4c.toByte()),
    GET_HFP_EXT_STA_RSP(0x4d.toByte()),
    GET_SRC_DEV_NO_REQ(0x4e.toByte()),
    GET_SRC_DEV_NO_RSP(0x4f.toByte()),
    GET_HFP_PSKEY_REQ(0x50.toByte()),
    GET_HFP_PSKEY_RSP(0x51.toByte()),
    GET_AG_PSKEY_REQ(0x52.toByte()),
    GET_AG_PSKEY_RSP(0x53.toByte()),
    GET_HFP_LOCAL_NAME_REQ(0x54.toByte()),
    GET_HFP_LOCAL_NAME_RSP(0x55.toByte()),
    GET_AG_LOCAL_NAME_REQ(0x56.toByte()),
    GET_AG_LOCAL_NAME_RSP(0x57.toByte()),
    GET_HFP_VRESION_REQ(0x58.toByte()),
    GET_HFP_VRESION_RSP(0x59.toByte()),
    GET_AG_VRESION_REQ(0x5a.toByte()),
    GET_AG_VRESION_RSP(0x5b.toByte()),
    GET_HFP_FEATURE_REQ(0x5c.toByte()),
    GET_HFP_FEATURE_RSP(0x5d.toByte()),
    GET_AG_FEATURE_REQ(0x5e.toByte()),
    GET_AG_FEATURE_RSP(0x5f.toByte()),
    GET_HFP_DIAL_REQ(0x60.toByte()),
    GET_HFP_DIAL_RSP(0x61.toByte()),
    GET_HFP_RSSI_REQ(0x70.toByte()),
    GET_HFP_RSSI_RSP(0x71.toByte()),
    GET_HFP_BDA_REQ(0x78.toByte()),
    GET_HFP_BDA_RSP(0x79.toByte()),
    GET_AG_BDA_REQ(0x7a.toByte()),
    GET_AG_BDA_RSP(0x7b.toByte());
}

