package com.example.imagebtapp_v001b001

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.core.view.size
import kotlinx.android.synthetic.main.fragment_audio_para_set.*
import kotlinx.android.synthetic.main.fragment_audio_para_set.rdGpParaAudioPara
import java.lang.Integer.parseInt

class FragmentAudioParaSet : Fragment() {
    private var isFragmentReady = false

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
        isFragmentReady = true
        rdGpDevAudioPara.visibility = GONE
        for (i in BtDevUnit.paraDataA6.size until rdGpParaAudioPara.size) {
            when (i) {
                0 -> rdAudioPara0.visibility = GONE
                1 -> rdAudioPara1.visibility = GONE
                2 -> rdAudioPara2.visibility = GONE
                3 -> rdAudioPara3.visibility = GONE
                4 -> rdAudioPara4.visibility = GONE
                5 -> rdAudioPara5.visibility = GONE
                6 -> rdAudioPara6.visibility = GONE
                7 -> rdAudioPara7.visibility = GONE
                8 -> rdAudioPara8.visibility = GONE
                9 -> rdAudioPara9.visibility = GONE
                10 -> rdAudioPara10.visibility = GONE
                11 -> rdAudioPara11.visibility = GONE
                12 -> rdAudioPara12.visibility = GONE
                13 -> rdAudioPara13.visibility = GONE
                14 -> rdAudioPara14.visibility = GONE
                15 -> rdAudioPara15.visibility = GONE
            }
        }
        btnAudioParaWrite.setOnClickListener {
            // rdGpParaAudioPara.check(rdGpParaAudioPara.checkedRadioButtonId)
            var devicePresent: Boolean
            var strList: List<String>

            (activity as DevUnitMsg).setVibrator(200)
            for (dev in 0..BtDevUnit.deviceNo) {
                for (i in 0..1) {
                    val sendMsg = BtDevMsg(0, 0)

                    strList = when ((activity as DevUnitMsg).getDevType(dev)) {
                        "A6_BT" -> {
                            devicePresent = true
                            Logger.d(LogGbl, "select A6 audio parameter")
                            BtDevUnit.paraDataA6[when (rdGpParaAudioPara.checkedRadioButtonId) {
                                R.id.rdAudioPara0 -> 0
                                R.id.rdAudioPara1 -> 1
                                R.id.rdAudioPara2 -> 2
                                R.id.rdAudioPara3 -> 3
                                R.id.rdAudioPara4 -> 4
                                R.id.rdAudioPara5 -> 5
                                R.id.rdAudioPara6 -> 6
                                R.id.rdAudioPara7 -> 7
                                R.id.rdAudioPara8 -> 8
                                R.id.rdAudioPara9 -> 9
                                R.id.rdAudioPara10 -> 10
                                R.id.rdAudioPara11 -> 11
                                R.id.rdAudioPara12 -> 12
                                R.id.rdAudioPara13 -> 13
                                R.id.rdAudioPara14 -> 14
                                R.id.rdAudioPara15 -> 15
                                else -> 0
                            }][i].split(' ')
                        }
                        "A7_BT" -> {
                            devicePresent = true
                            Logger.d(LogGbl, "select A7 audio parameter")
                            BtDevUnit.paraDataA7[when (rdGpParaAudioPara.checkedRadioButtonId) {
                                R.id.rdAudioPara0 -> 0
                                R.id.rdAudioPara1 -> 1
                                R.id.rdAudioPara2 -> 2
                                R.id.rdAudioPara3 -> 3
                                R.id.rdAudioPara4 -> 4
                                R.id.rdAudioPara5 -> 5
                                R.id.rdAudioPara6 -> 6
                                R.id.rdAudioPara7 -> 7
                                R.id.rdAudioPara8 -> 8
                                R.id.rdAudioPara9 -> 9
                                R.id.rdAudioPara10 -> 10
                                R.id.rdAudioPara11 -> 11
                                R.id.rdAudioPara12 -> 12
                                R.id.rdAudioPara13 -> 13
                                R.id.rdAudioPara14 -> 14
                                R.id.rdAudioPara15 -> 15
                                else -> 0
                            }][i].split(' ')
                        }
                        else -> {
                            devicePresent = false
                            Logger.d(LogGbl, "select none audio parameter")
                            arrayListOf()
                        }
                    }
                    if (devicePresent == true) {
                        sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
                        sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
                        sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
                        sendMsg.btCmd[3] = when (dev) {
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
                        when (rdGpModeAudioPara.checkedRadioButtonId) {
                            R.id.rdModeBtAudioPara -> {
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
                            R.id.rdModeVcsAudioPara -> {
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
                            R.id.rdModeWireAudioPara -> {
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
                            R.id.rdModeUsbAudioPara -> {
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
                        Handler().postDelayed(
                            { (activity as DevUnitMsg).sendBtServiceMsg(sendMsg) },
                            (100 * dev).toLong()
                        )
                    }
                }
            }
            Logger.d(LogGbl, "audio parameter activity created")
        }

        btnAudioParaUsbVolSync.setOnClickListener {
            val sendMsg = BtDevMsg(0, 0)
            val strList = BtDevUnit.featureBdaddrFilter.split(':')

            (activity as DevUnitMsg).setVibrator(200)
            (activity as DevUnitMsg).getBtDevUnitList()[0].featureHfp = (activity as DevUnitMsg).getBtDevUnitList()[0].featureHfp.and(1.shl(3).inv())
            sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[3] = CmdId.CMD_DEV_SRC.value
            sendMsg.btCmd[4] = CmdId.SET_HFP_FEATURE_REQ.value
            sendMsg.btCmd[5] = 0x13
            sendMsg.btCmd[6] = (activity as DevUnitMsg).getBtDevUnitList()[0].featureHfp.shr(8).toByte()
            sendMsg.btCmd[7] = (activity as DevUnitMsg).getBtDevUnitList()[0].featureHfp.and(0xff).toByte()
            sendMsg.btCmd[8] = BtDevUnit.maxAgNo.and(0xff).toByte()
            sendMsg.btCmd[9] = BtDevUnit.maxTalkNo.and(0xff).toByte()
            sendMsg.btCmd[10] = 0x00
            sendMsg.btCmd[11] = Integer.parseInt(strList[3], 16).toByte()
            sendMsg.btCmd[12] = Integer.parseInt(strList[4], 16).toByte()
            sendMsg.btCmd[13] = Integer.parseInt(strList[5], 16).toByte()
            sendMsg.btCmd[14] = Integer.parseInt(strList[2], 16).toByte()
            sendMsg.btCmd[15] = Integer.parseInt(strList[0], 16).toByte()
            sendMsg.btCmd[16] = Integer.parseInt(strList[1], 16).toByte()
            sendMsg.btCmd[17] = (activity as DevUnitMsg).getBtDevUnitList()[0].ledLightHfp[0].shr(8).toByte()
            sendMsg.btCmd[18] = (activity as DevUnitMsg).getBtDevUnitList()[0].ledLightHfp[0].and(0xff).toByte()
            sendMsg.btCmd[19] = (activity as DevUnitMsg).getBtDevUnitList()[0].ledLightHfp[1].shr(8).toByte()
            sendMsg.btCmd[20] = (activity as DevUnitMsg).getBtDevUnitList()[0].ledLightHfp[1].and(0xff).toByte()
            sendMsg.btCmd[21] = (activity as DevUnitMsg).getBtDevUnitList()[0].ledLightHfp[2].shr(8).toByte()
            sendMsg.btCmd[22] = (activity as DevUnitMsg).getBtDevUnitList()[0].ledLightHfp[2].and(0xff).toByte()
            sendMsg.btCmd[23] = (activity as DevUnitMsg).getBtDevUnitList()[0].ledLightHfp[3].shr(8).toByte()
            sendMsg.btCmd[24] = (activity as DevUnitMsg).getBtDevUnitList()[0].ledLightHfp[3].and(0xff).toByte()
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)

            Handler().postDelayed({
                var sendMsgCon = BtDevMsg(0, 1)

                sendMsgCon.btCmd[0] = CmdId.CMD_HEAD_FF.value
                sendMsgCon.btCmd[1] = CmdId.CMD_HEAD_55.value
                sendMsgCon.btCmd[2] = CmdId.CMD_DEV_HOST.value
                sendMsgCon.btCmd[3] = CmdId.CMD_DEV_HOST.value
                sendMsgCon.btCmd[4] = CmdId.SET_INT_CON_REQ.value
                sendMsgCon.btCmd[5] = 0x07
                sendMsgCon.btCmd[6] = 0x00
                sendMsgCon.btCmd[7] = 0x00
                sendMsgCon.btCmd[8] = 0x00
                sendMsgCon.btCmd[9] = 0x00
                sendMsgCon.btCmd[10] = 0x00
                sendMsgCon.btCmd[11] = 0x00
                sendMsgCon.btCmd[12] = 0x00
                (activity as DevUnitMsg).sendBtServiceMsg(sendMsgCon)
            }, 200)
        }

        btnAudioParaUsbVolMax.setOnClickListener {
            val sendMsg = BtDevMsg(0, 0)
            val strList = BtDevUnit.featureBdaddrFilter.split(':')

            (activity as DevUnitMsg).setVibrator(200)
            (activity as DevUnitMsg).getBtDevUnitList()[0].featureHfp = (activity as DevUnitMsg).getBtDevUnitList()[0].featureHfp.or(1.shl(3))
            sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[3] = CmdId.CMD_DEV_SRC.value
            sendMsg.btCmd[4] = CmdId.SET_HFP_FEATURE_REQ.value
            sendMsg.btCmd[5] = 0x13
            sendMsg.btCmd[6] = (activity as DevUnitMsg).getBtDevUnitList()[0].featureHfp.shr(8).toByte()
            sendMsg.btCmd[7] = (activity as DevUnitMsg).getBtDevUnitList()[0].featureHfp.and(0xff).toByte()
            sendMsg.btCmd[8] = BtDevUnit.maxAgNo.and(0xff).toByte()
            sendMsg.btCmd[9] = BtDevUnit.maxTalkNo.and(0xff).toByte()
            sendMsg.btCmd[10] = 0x00
            sendMsg.btCmd[11] = Integer.parseInt(strList[3], 16).toByte()
            sendMsg.btCmd[12] = Integer.parseInt(strList[4], 16).toByte()
            sendMsg.btCmd[13] = Integer.parseInt(strList[5], 16).toByte()
            sendMsg.btCmd[14] = Integer.parseInt(strList[2], 16).toByte()
            sendMsg.btCmd[15] = Integer.parseInt(strList[0], 16).toByte()
            sendMsg.btCmd[16] = Integer.parseInt(strList[1], 16).toByte()
            sendMsg.btCmd[17] = (activity as DevUnitMsg).getBtDevUnitList()[0].ledLightHfp[0].shr(8).toByte()
            sendMsg.btCmd[18] = (activity as DevUnitMsg).getBtDevUnitList()[0].ledLightHfp[0].and(0xff).toByte()
            sendMsg.btCmd[19] = (activity as DevUnitMsg).getBtDevUnitList()[0].ledLightHfp[1].shr(8).toByte()
            sendMsg.btCmd[20] = (activity as DevUnitMsg).getBtDevUnitList()[0].ledLightHfp[1].and(0xff).toByte()
            sendMsg.btCmd[21] = (activity as DevUnitMsg).getBtDevUnitList()[0].ledLightHfp[2].shr(8).toByte()
            sendMsg.btCmd[22] = (activity as DevUnitMsg).getBtDevUnitList()[0].ledLightHfp[2].and(0xff).toByte()
            sendMsg.btCmd[23] = (activity as DevUnitMsg).getBtDevUnitList()[0].ledLightHfp[3].shr(8).toByte()
            sendMsg.btCmd[24] = (activity as DevUnitMsg).getBtDevUnitList()[0].ledLightHfp[3].and(0xff).toByte()
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)

            Handler().postDelayed({
                var sendMsgCon = BtDevMsg(0, 1)

                sendMsgCon.btCmd[0] = CmdId.CMD_HEAD_FF.value
                sendMsgCon.btCmd[1] = CmdId.CMD_HEAD_55.value
                sendMsgCon.btCmd[2] = CmdId.CMD_DEV_HOST.value
                sendMsgCon.btCmd[3] = CmdId.CMD_DEV_HOST.value
                sendMsgCon.btCmd[4] = CmdId.SET_INT_CON_REQ.value
                sendMsgCon.btCmd[5] = 0x07
                sendMsgCon.btCmd[6] = 0x00
                sendMsgCon.btCmd[7] = 0x00
                sendMsgCon.btCmd[8] = 0x00
                sendMsgCon.btCmd[9] = 0x00
                sendMsgCon.btCmd[10] = 0x00
                sendMsgCon.btCmd[11] = 0x00
                sendMsgCon.btCmd[12] = 0x00
                (activity as DevUnitMsg).sendBtServiceMsg(sendMsgCon)
            }, 200)
        }
    }

    override fun onPause() {
        super.onPause()

        Logger.d(LogGbl, "FragmentAudioParaSet on Pause")
        isFragmentReady = false
    }

    fun updateData() {
/*
        if(isFragmentReady == false) {
            Handler().postDelayed({updateData()}, 100)
            Logger.d(LogGbl, "fragment not ready")
            return
        }
*/
        when (BtDevUnit.sppStateCon) {
            0x00.toByte() -> {
                btnAudioParaRead.visibility = View.VISIBLE
                btnAudioParaWrite.visibility = View.VISIBLE
                btnAudioParaUsbVolMax.visibility = View.VISIBLE
                btnAudioParaUsbVolSync.visibility = View.VISIBLE
            }
            0x01.toByte() -> {
                btnAudioParaRead.visibility = View.INVISIBLE
                btnAudioParaWrite.visibility = View.INVISIBLE
                btnAudioParaUsbVolMax.visibility = View.INVISIBLE
                btnAudioParaUsbVolSync.visibility = View.INVISIBLE
            }
            else -> {
                btnAudioParaRead.visibility = View.INVISIBLE
                btnAudioParaWrite.visibility = View.INVISIBLE
                btnAudioParaUsbVolMax.visibility = View.INVISIBLE
                btnAudioParaUsbVolSync.visibility = View.INVISIBLE
            }
        }

        btnAudioParaWrite.visibility =
            when (BtDevUnit.sppStateCon) {
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
        btnAudioParaRead.visibility =
            when (BtDevUnit.sppStateCon) {
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
