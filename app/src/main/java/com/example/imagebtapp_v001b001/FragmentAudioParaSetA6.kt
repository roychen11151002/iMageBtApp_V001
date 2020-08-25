package com.example.imagebtapp_v001b001

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.ViewGroup
import androidx.core.view.size
import kotlinx.android.synthetic.main.fragment_audio_para_set_a6.*
import kotlinx.android.synthetic.main.fragment_con_state.*
import java.lang.Integer.parseInt

class FragmentAudioParaSetA6 : Fragment() {
    val paraData = arrayOf(arrayOf("2284 2285 0041 e18a 0066 f70c 6666 6603 6666 3333 ffe8 ffff 0000 1000 0000 7ee0 00f2 0004 b659 2000 0000 0004 197f 9999 ffff 0000 000f 0002 0000 80c9 13ff 001f 0903 886f d37a a8cb 73fa 19d3 fc69 a8cb e926 d25e da18 4ef8 bc7b 8eda 2600 da18 f800 3b89 606b 3f2a 3b37 606b 1673 8900 94c7 8000 0000 0001",
                                                       "2285 0000 4100 8000 0000 0001 0000 0000 0030 3300 3333 0003 1006 000b 80c9 3f30 0c00 cccc 0000 0210 2802 7a26 0000 0000 0000 000c 0000 0804 0199 0400 0000 0109"),
                                               arrayOf("227c 227d 00de e188 0066 f70c 6666 6603 6666 3333 ffe8 ffff 0000 7ee0 00f9 0004 5b2d 0600 cccc 0004 197f 9999 ffff 0000 0014 0002 0000 00cc 0000 0000 0000 0000 0000 0018 3300 3333 0003 0803 000b 00cc 3f30 0c00 cccc 0000 0108 2802 7a26 0000 0000 0000 0004 0000 0b53",
                                                       "227d 0000 de00 8080 0000 0199 0109"),
                                               arrayOf("227c 227d 0041 e188 0066 f70c 6666 6603 6666 3333 ffe8 ffff 0000 7ee0 00f9 0004 5b2d 0800 0000 0004 0c7f cccc ffff 0000 0014 0002 0000 00cb 13ff 001f 0903 886f d37a a8cb 73fa 19d3 fc69 a8cb e926 d25e da18 4ef8 bc7b 8eda 2600 da18 f800 3b89 606b 3f2a 3b37 606b 1673 8900 94c7 8000 0000 0001",
                                                       "227d 0000 4100 8000 0000 0001 0000 0000 0030 3300 3333 0003 1006 000b 00cb 3f30 0c00 cccc 0000 0210 2802 7a26 0000 0000 0000 000c 0000 0804 0199 0400 0000 0109"),
                                               arrayOf("227c 227d 0041 e188 0066 f70c 6666 6603 6666 3333 ffe8 ffff 0000 7ee0 00f9 0004 5b2d 0800 0000 0004 197f 9999 ffff 0000 0014 0002 0000 00cd 13ff 001f 0903 886f d37a a8cb 73fa 19d3 fc69 a8cb e926 d25e da18 4ef8 bc7b 8eda 2600 da18 f800 3b89 606b 3f2a 3b37 606b 1673 8900 94c7 8000 0000 0001",
                                                       "227d 0000 4100 8000 0000 0001 0000 0000 0030 3300 3333 0003 1006 000b 00cd 3f30 0c00 cccc 0000 0210 2802 7a26 0000 0000 0000 000c 0000 0804 0199 0400 0000 0109"),
                                               arrayOf("227c 227d 00de e188 0466 f60c 6666 6603 6666 3333 ffe8 ffff 0000 7ee0 00f9 0004 5b2d 0800 0000 0004 197f 9999 ffff 0000 000f 0002 0000 80c9 0000 0000 0000 0000 0000 0018 3300 3333 0003 0803 000b 80c9 3f30 0c00 cccc 0000 0108 1906 8a13 5316 0000 0000 0004 7f00 ffff",
                                                       "227d 0000 de00 8000 0000 0199"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.d(LogGbl, "FragmentConState on Create")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_audio_para_set_a6, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rdGpDevAudioParaA6.visibility = INVISIBLE
        for(i in paraData.size until rdGpParaAudioPara.size) {
            when (i) {
                0 -> rdAudioPara0A6.visibility = GONE
                1 -> rdAudioPara1A6.visibility = GONE
                2 -> rdAudioPara2A6.visibility = GONE
                3 -> rdAudioPara3A6.visibility = GONE
                4 -> rdAudioPara4A6.visibility = GONE
                5 -> rdAudioPara5A6.visibility = GONE
                6 -> rdAudioPara6A6.visibility = GONE
                7 -> rdAudioPara7A6.visibility = GONE
                8 -> rdAudioPara8A6.visibility = GONE
                9 -> rdAudioPara9A6.visibility = GONE
                10 -> rdAudioPara10A6.visibility = GONE
                11 -> rdAudioPara11A6.visibility = GONE
                12 -> rdAudioPara12A6.visibility = GONE
                13 -> rdAudioPara13A6.visibility = GONE
                14 -> rdAudioPara14A6.visibility = GONE
                15 -> rdAudioPara15A6.visibility = GONE
            }
        }
        btnAudioParaWriteA6.setOnClickListener {
            // rdGpParaAudioPara.check(rdGpParaAudioPara.checkedRadioButtonId)
            for(dev in 0 .. BtDevUnit.deviceNo) {
                if ((activity as DevUnitMsg).getBtDevUnitList()[dev].nameLocalHfp.substring(0, 11).compareTo("iMage A6_BT") == 0) {
                    for (i in 0 .. 1) {
                        val sendMsg = BtDevMsg(0, 0)
                        var strList =
                            paraData[when (rdGpParaAudioPara.checkedRadioButtonId) {
                                R.id.rdAudioPara0A6 -> 0
                                R.id.rdAudioPara1A6 -> 1
                                R.id.rdAudioPara2A6 -> 2
                                R.id.rdAudioPara3A6 -> 3
                                R.id.rdAudioPara4A6 -> 4
                                R.id.rdAudioPara5A6 -> 5
                                R.id.rdAudioPara6A6 -> 6
                                R.id.rdAudioPara7A6 -> 7
                                R.id.rdAudioPara8A6 -> 8
                                R.id.rdAudioPara9A6 -> 9
                                R.id.rdAudioPara10A6 -> 10
                                R.id.rdAudioPara11A6 -> 11
                                R.id.rdAudioPara12A6 -> 12
                                R.id.rdAudioPara13A6 -> 13
                                R.id.rdAudioPara14A6 -> 14
                                R.id.rdAudioPara15A6 -> 15
                                else -> 0
                            }][i].split(' ')

                        sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
                        sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
                        sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
                        sendMsg.btCmd[3] = when(dev) {
                            0 -> 0x30
                            1 -> 0x00
                            2 -> 0x08
                            3 -> 0x10
                            4 -> 0x18
                            5 -> 0x20
                            6 -> 0x28
                            else -> 0x38
                        }
                        sendMsg.btCmd[4] = CmdId.SET_HFP_PSKEY_REQ.value
                        sendMsg.btCmd[5] = (strList.size * 2 + 2).toByte()
                        sendMsg.btCmd[6] = 0x00
                        when (rdGpModeAudioParaA6.checkedRadioButtonId) {
                            R.id.rdModeBtAudioParaA6 -> {
                                sendMsg.btCmd[7] = (84 + i).toByte()
                                if (i == 0) {
                                    sendMsg.btCmd[8] = 0x22
                                    sendMsg.btCmd[9] = 0x7a.toByte()
                                    if (strList[1] != "0000") {
                                        sendMsg.btCmd[10] = 0x22
                                        sendMsg.btCmd[11] = 0x7b.toByte()
                                    }
                                } else {
                                    sendMsg.btCmd[8] = 0x22
                                    sendMsg.btCmd[9] = 0x7b.toByte()
                                }
                            }
                            R.id.rdModeVcsAudioParaA6 -> {
                                sendMsg.btCmd[7] = (86 + i).toByte()
                                if (i == 0) {
                                    sendMsg.btCmd[8] = 0x22
                                    sendMsg.btCmd[9] = 0x7c.toByte()
                                    if (strList[1] != "0000") {
                                        sendMsg.btCmd[10] = 0x22
                                        sendMsg.btCmd[11] = 0x7d.toByte()
                                    }
                                } else {
                                    sendMsg.btCmd[8] = 0x22
                                    sendMsg.btCmd[9] = 0x7d.toByte()
                                }
                            }
                            R.id.rdModeWireAudioParaA6 -> {
                                sendMsg.btCmd[7] = (88 + i).toByte()
                                if (i == 0) {
                                    sendMsg.btCmd[8] = 0x22
                                    sendMsg.btCmd[9] = 0x7e.toByte()
                                    if (strList[1] != "0000") {
                                        sendMsg.btCmd[10] = 0x22
                                        sendMsg.btCmd[11] = 0x7f.toByte()
                                    }
                                } else {
                                    sendMsg.btCmd[8] = 0x22
                                    sendMsg.btCmd[9] = 0x7f.toByte()
                                }
                            }
                            R.id.rdModeUsbAudioParaA6 -> {
                                sendMsg.btCmd[7] = (94 + i).toByte()
                                if (i == 0) {
                                    sendMsg.btCmd[8] = 0x22
                                    sendMsg.btCmd[9] = 0x84.toByte()
                                    if (strList[1] != "0000") {
                                        sendMsg.btCmd[10] = 0x22
                                        sendMsg.btCmd[11] = 0x85.toByte()
                                    }
                                } else {
                                    sendMsg.btCmd[8] = 0x22
                                    sendMsg.btCmd[9] = 0x85.toByte()
                                }
                            }
                            else -> {
                                sendMsg.btCmd[7] = (94 + i).toByte()
                                if (i == 0) {
                                    sendMsg.btCmd[8] = 0x22
                                    sendMsg.btCmd[9] = 0x84.toByte()
                                    if (strList[1] != "0000") {
                                        sendMsg.btCmd[10] = 0x22
                                        sendMsg.btCmd[11] = 0x85.toByte()
                                    }
                                } else {
                                    sendMsg.btCmd[8] = 0x22
                                    sendMsg.btCmd[9] = 0x85.toByte()
                                }
                            }
                        }
                        for (j in 2 until strList.size) {
                            sendMsg.btCmd[j * 2 + 8] =
                                parseInt(strList[j], 16).shr(8).and(0xff).toByte()
                            sendMsg.btCmd[j * 2 + 8 + 1] =
                                parseInt(strList[j], 16).and(0xff).toByte()
                            Logger.d(LogGbl, "${strList[j]}")
                        }
                        Logger.d(LogGbl, "audio parameter send")
                        Handler().postDelayed({(activity as DevUnitMsg).sendBtServiceMsg(sendMsg)}, (100 * dev).toLong())
                    }
                }
            }
        }
        Logger.d(LogGbl, "audio parameter activity created")
    }

    fun updateData() {
        btnAudioParaWriteA6.visibility =
            when(BtDevUnit.sppStateCon) {
                0x00.toByte() -> {
                    View.VISIBLE
                }
                0x01.toByte() -> {
                    View.INVISIBLE
                }
                else -> {
                    View.INVISIBLE
                }
            }
        btnAudioParaReadA6.visibility =
            when(BtDevUnit.sppStateCon) {
                0x00.toByte() -> {
                    View.VISIBLE
                }
                0x01.toByte() -> {
                    View.INVISIBLE
                }
                else -> {
                    View.INVISIBLE
                }
            }
    }
}
