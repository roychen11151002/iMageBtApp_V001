package com.example.imagebtapp_v001b001

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_audio_para_set.*
import java.lang.Integer.parseInt

class FragmentAudioParaSet : Fragment() {
    val paraData = arrayOf(arrayOf("2284 2285 0041 e18a 0066 f70c 6666 6603 6666 3333 ffe8 ffff 0000 1000 0000 7ee0 00f2 0004 b659 2000 0000 0004 197f 9999 ffff 0000 000f 0002 0000 80c9 13ff 001f 0903 886f d37a a8cb 73fa 19d3 fc69 a8cb e926 d25e da18 4ef8 bc7b 8eda 2600 da18 f800 3b89 606b 3f2a 3b37 606b 1673 8900 94c7 8000 0000 0001",
                                                        "2285 0000 4100 8000 0000 0001 0000 0000 0030 3300 3333 0003 1006 000b 80c9 3f30 0c00 cccc 0000 0210 2802 7a26 0000 0000 0000 000c 0000 0804 0199 0400 0000 0109"),
                                               arrayOf("227a 227b 0041 e18a 0066 f70c 6666 6603 6666 3333 ffe8 ffff 0000 1000 0000 7ee0 00f2 0004 b659 2000 0000 0004 197f 9999 ffff 0000 0010 0002 0000 80c9 13ff 001f 0903 886f d37a a8cb 73fa 19d3 fc69 a8cb e926 d25e da18 4ef8 bc7b 8eda 2600 da18 f800 3b89 606b 3f2a 3b37 606b 1673 8900 94c7 8000 0000 0001",
                                                       "227b 0000 4100 8000 0000 0001 0000 0000 0030 3300 3333 0003 1006 000b 80c9 3f30 0c00 cccc 0000 0210 2802 7a26 0000 0000 0000 000c 0000 0804 0199 0400 0000 0109"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.d(LogGbl, "FragmentConState on Create")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_audio_para_set, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btnAudioParaWrite.setOnClickListener {
            val sendMsg = arrayOf(BtDevMsg(0, 0), BtDevMsg(0, 0))

            for(i in 0..1) {
                var strList =
                    paraData[when(rdGpParaAudioPara.checkedRadioButtonId) {
                                R.id.rdAudioPara0 -> 0
                                R.id.rdAudioPara1 -> 1
                                R.id.rdAudioPara2 -> 2
                                R.id.rdAudioPara3 -> 3
                                else -> 0
                            }][i].split(' ')

                sendMsg[i].btCmd[0] = CmdId.CMD_HEAD_FF.value
                sendMsg[i].btCmd[1] = CmdId.CMD_HEAD_55.value
                sendMsg[i].btCmd[2] = CmdId.CMD_DEV_HOST.value
                sendMsg[i].btCmd[3] = when(rdGpDevAudioPara.checkedRadioButtonId) {
                        R.id.rdA6AudioPara -> 0x38
                        R.id.rdA7AudioPara -> 0x38
                        R.id.rdAllAudioPara -> 0x38
                        else -> 0x38
                }
                sendMsg[i].btCmd[4] = CmdId.SET_HFP_PSKEY_REQ.value
                sendMsg[i].btCmd[5] = (strList.size * 2 + 2).toByte()
                sendMsg[i].btCmd[6] = 0x00
                when(rdGpModeAudioPara.checkedRadioButtonId) {
                    R.id.rdModeBtAudioPara -> {
                        sendMsg[i].btCmd[7] = (84 + i).toByte()
                        if(i == 0) {
                            sendMsg[i].btCmd[8] = 0x22
                            sendMsg[i].btCmd[9] = 0x7a.toByte()
                            if(strList[1] != "0000") {
                                sendMsg[i].btCmd[10] = 0x22
                                sendMsg[i].btCmd[11] = 0x7b.toByte()
                            }
                        }
                        else {
                            sendMsg[i].btCmd[8] = 0x22
                            sendMsg[i].btCmd[9] = 0x7b.toByte()
                        }
                    }
                    R.id.rdModeVcsAudioPara -> {
                        sendMsg[i].btCmd[7] = (86 + i).toByte()
                        if(i == 0) {
                            sendMsg[i].btCmd[8] = 0x22
                            sendMsg[i].btCmd[9] = 0x7c.toByte()
                            if(strList[1] != "0000") {
                                sendMsg[i].btCmd[10] = 0x22
                                sendMsg[i].btCmd[11] = 0x7d.toByte()
                            }

                        }
                        else {
                            sendMsg[i].btCmd[8] = 0x22
                            sendMsg[i].btCmd[9] = 0x7d.toByte()
                        }
                    }
                    R.id.rdModeWireAudioPara -> {
                        sendMsg[i].btCmd[7] = (88 + i).toByte()
                        if(i == 0) {
                            sendMsg[i].btCmd[8] = 0x22
                            sendMsg[i].btCmd[9] = 0x7e.toByte()
                            if(strList[1] != "0000") {
                                sendMsg[i].btCmd[10] = 0x22
                                sendMsg[i].btCmd[11] = 0x7f.toByte()
                            }
                        }
                        else {
                            sendMsg[i].btCmd[8] = 0x22
                            sendMsg[i].btCmd[9] = 0x7f.toByte()
                        }
                    }
                    R.id.rdModeUsbAudioPara -> {
                        sendMsg[i].btCmd[7] = (94 + i).toByte()
                        if(i == 0) {
                            sendMsg[i].btCmd[8] = 0x22
                            sendMsg[i].btCmd[9] = 0x84.toByte()
                            if(strList[1] != "0000") {
                                sendMsg[i].btCmd[10] = 0x22
                                sendMsg[i].btCmd[11] = 0x85.toByte()
                            }
                        }
                        else {
                            sendMsg[i].btCmd[8] = 0x22
                            sendMsg[i].btCmd[9] = 0x85.toByte()
                        }
                    }
                    else -> {
                        sendMsg[i].btCmd[7] = (94 + i).toByte()
                        if(i == 0) {
                            sendMsg[i].btCmd[8] = 0x22
                            sendMsg[i].btCmd[9] = 0x84.toByte()
                            if(strList[1] != "0000") {
                                sendMsg[i].btCmd[10] = 0x22
                                sendMsg[i].btCmd[11] = 0x85.toByte()
                            }
                        }
                        else {
                            sendMsg[i].btCmd[8] = 0x22
                            sendMsg[i].btCmd[9] = 0x85.toByte()
                        }
                    }
                }
                for(j in 2 until strList.size) {
                    sendMsg[i].btCmd[j * 2 + 8] = parseInt(strList[j], 16).shr(8).and(0xff).toByte()
                    sendMsg[i].btCmd[j * 2 + 8 + 1] = parseInt(strList[j], 16).and(0xff).toByte()
                    Logger.d(LogGbl, "${strList[j]}")
                }
                (activity as DevUnitMsg).sendBtServiceMsg(sendMsg[i])
            }
            // (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
            Logger.d(LogGbl, "audio parameter send")
        }
    }
}
