package com.example.imagebtapp_v001b001

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.SeekBar
import kotlinx.android.synthetic.main.fragment_feature_set.*

class FragmentFeatureSet : Fragment() {
    private var srcDevItem = 0
    private var isFragmentReady = false
    private var isCheckReady = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.d(LogGbl, "FragmentConState on Create")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feature_set, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
/*
         rdAgAllFeature.visibility =
            if ((activity as DevUnitMsg).getBtDevUnitList()[0].nameLocalHfp.substring(0, 12).compareTo("iMage M6_SRC") == 0)
                View.VISIBLE
            else
                View.GONE
 */
        isFragmentReady = true
        updateData()
        if((activity as DevUnitMsg).getBtDevUnitList().size == 1) {
            rdAgAllFeature.isEnabled = false
            rdHfpAllFeature.isEnabled = false
        }
        else {
            rdAgAllFeature.isEnabled = true
            rdHfpAllFeature.isEnabled = true
        }

        btnFeatureRead.setOnClickListener {
            val sendMsg = BtDevMsg(0, 0)
            val sendMsgMode = BtDevMsg(0, 0)

            (activity as DevUnitMsg).setVibrator(200)
            deviceItemGet()
            sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[3] =
                when(rdGpDevFeature.checkedRadioButtonId) {
                    R.id.rdSrcFeature -> 0x30
                    R.id.rdAgAllFeature -> 0x38
                    R.id.rdHfpAllFeature -> 0x38
                    else -> 0x38
                }
            sendMsg.btCmd[4] =
                when(rdGpDevFeature.checkedRadioButtonId) {
                    R.id.rdSrcFeature -> CmdId.GET_HFP_FEATURE_REQ.value
                    R.id.rdAgAllFeature -> CmdId.GET_AG_FEATURE_REQ.value
                    R.id.rdHfpAllFeature -> CmdId.GET_HFP_FEATURE_REQ.value
                    else -> CmdId.GET_HFP_FEATURE_REQ.value
                }
            sendMsg.btCmd[5] = 0x00
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)

            sendMsgMode.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsgMode.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsgMode.btCmd[2] = CmdId.CMD_DEV_HOST.value
                when(rdGpDevFeature.checkedRadioButtonId) {
                R.id.rdSrcFeature -> {
                    sendMsgMode.btCmd[3] = 0x30
                    sendMsgMode.btCmd[4] = CmdId.GET_HFP_PSKEY_REQ.value
                }
                R.id.rdAgAllFeature -> {
                    sendMsgMode.btCmd[3] = 0x38
                    sendMsgMode.btCmd[4] = CmdId.GET_AG_PSKEY_REQ.value
                }
                R.id.rdHfpAllFeature -> {
                    sendMsgMode.btCmd[3] = 0x38
                    sendMsgMode.btCmd[4] = CmdId.GET_HFP_PSKEY_REQ.value
                }
            }
            sendMsgMode.btCmd[5] = 0x02
            sendMsgMode.btCmd[6] = 0x00
            sendMsgMode.btCmd[7] = 9
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsgMode)
        }

        btnFeatureWrite.setOnClickListener {
            val sendMsgMode = BtDevMsg(0, 0)
            val sendMsg = BtDevMsg(0, 0)
            val strList = BtDevUnit.featureBdaddrFilter.split(':')

            (activity as DevUnitMsg).setVibrator(200)
            deviceItemGet()
            (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].featureMode = (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].featureMode.and(0x0f000000.inv())
            (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].featureMode =
                when(rdGpModeFeature.checkedRadioButtonId) {
                    R.id.rdModeUsbFeature ->  (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].featureMode.or(0x01000000)
                    R.id.rdModeBtFeature -> (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].featureMode.or(0x02000000)
                    R.id.rdModeVcsFeature -> (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].featureMode.or(0x04000000)
                    R.id.rdModeWireFeature -> (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].featureMode.or(0x08000000)
                    else -> (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].featureMode.or(0x01000000)
                }

            sendMsgMode.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsgMode.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsgMode.btCmd[2] = CmdId.CMD_DEV_HOST.value
            when(rdGpDevFeature.checkedRadioButtonId) {
                R.id.rdSrcFeature -> {
                    sendMsgMode.btCmd[3] = 0x30
                    sendMsgMode.btCmd[4] = CmdId.SET_HFP_PSKEY_REQ.value
                }
                R.id.rdAgAllFeature -> {
                    sendMsgMode.btCmd[3] = 0x38
                    sendMsgMode.btCmd[4] = CmdId.SET_AG_FEATURE_REQ.value
                }
                R.id.rdHfpAllFeature -> {
                    sendMsgMode.btCmd[3] = 0x38
                    sendMsgMode.btCmd[4] = CmdId.SET_HFP_PSKEY_REQ.value
                }
            }
            sendMsgMode.btCmd[5] = 0x6
            sendMsgMode.btCmd[6] = 0x00
            sendMsgMode.btCmd[7] = 9
            sendMsgMode.btCmd[8] = (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].featureMode.shr(24).toByte()
            sendMsgMode.btCmd[9] = (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].featureMode.shr(16).toByte()
            sendMsgMode.btCmd[10] = (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].featureMode.shr(8).toByte()
            sendMsgMode.btCmd[11] = (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].featureMode.shr(0).toByte()
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsgMode)

            sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[5] = 0x13
            when(rdGpDevFeature.checkedRadioButtonId) {
                R.id.rdSrcFeature -> {
                    sendMsg.btCmd[3] = 0x30
                    sendMsg.btCmd[4] = CmdId.SET_HFP_FEATURE_REQ.value
                    sendMsg.btCmd[6] = (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].featureHfp.shr(8).toByte()
                    sendMsg.btCmd[7] = (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].featureHfp.and(0xff).toByte()
                }
                R.id.rdAgAllFeature -> {
                    sendMsg.btCmd[3] = 0x38
                    sendMsg.btCmd[4] = CmdId.SET_AG_FEATURE_REQ.value
                    sendMsg.btCmd[6] = (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].featureAg.shr(8).toByte()
                    sendMsg.btCmd[7] = (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].featureAg.and(0xff).toByte()
                }
                R.id.rdHfpAllFeature -> {
                    sendMsg.btCmd[3] = 0x38
                    sendMsg.btCmd[4] = CmdId.SET_HFP_FEATURE_REQ.value
                    sendMsg.btCmd[6] = (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].featureHfp.shr(8).toByte()
                    sendMsg.btCmd[7] = (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].featureHfp.and(0xff).toByte()
                }
            }
            sendMsg.btCmd[8] = BtDevUnit.maxAgNo.and(0xff).toByte()
            sendMsg.btCmd[9] = BtDevUnit.maxTalkNo.and(0xff).toByte()
            sendMsg.btCmd[10] = 0x00
            sendMsg.btCmd[11] = Integer.parseInt(strList[3], 16).toByte()
            sendMsg.btCmd[12] = Integer.parseInt(strList[4], 16).toByte()
            sendMsg.btCmd[13] = Integer.parseInt(strList[5], 16).toByte()
            sendMsg.btCmd[14] = Integer.parseInt(strList[2], 16).toByte()
            sendMsg.btCmd[15] = Integer.parseInt(strList[0], 16).toByte()
            sendMsg.btCmd[16] = Integer.parseInt(strList[1], 16).toByte()
            sendMsg.btCmd[17] = seekLedPwr.progress.shr(8).toByte()
            sendMsg.btCmd[18] = seekLedPwr.progress.and(0xff).toByte()
            sendMsg.btCmd[19] = seekLedMfb.progress.shr(8).toByte()
            sendMsg.btCmd[20] = seekLedMfb.progress.and(0xff).toByte()
            sendMsg.btCmd[21] = seekLedBcb.progress.shr(8).toByte()
            sendMsg.btCmd[22] = seekLedBcb.progress.and(0xff).toByte()
            sendMsg.btCmd[23] = seekLedRev.progress.shr(8).toByte()
            sendMsg.btCmd[24] = seekLedRev.progress.and(0xff).toByte()
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
            if(rdGpDevFeature.checkedRadioButtonId == R.id.rdSrcFeature) {
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

        btnFeatureWrite.setOnLongClickListener {
            val strList = (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].bdaddrFilterHfp.split(':')

            AlertDialog.Builder(activity).setTitle(R.string.txvFunDefult).setPositiveButton(R.string.txvOk) { _, _ ->
                for(dev in 0 .. BtDevUnit.deviceNo) {
                    when ((activity as DevUnitMsg).getDevType(dev)) {
                        "A6" -> {
                            val sendMsg = BtDevMsg(0, 0)

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
                            sendMsg.btCmd[4] = CmdId.SET_HFP_FEATURE_REQ.value
                            sendMsg.btCmd[5] = 0x13
                            sendMsg.btCmd[6] = BtDevUnit.featuerA6.shr(8).toByte()
                            sendMsg.btCmd[7] = BtDevUnit.featuerA6.and(0xff).toByte()
                            sendMsg.btCmd[8] = BtDevUnit.featuerMaxAgNo.toByte()
                            sendMsg.btCmd[9] = BtDevUnit.featureMaxTalkNo.toByte()
                            sendMsg.btCmd[10] = 0x00
                            sendMsg.btCmd[11] = Integer.parseInt(strList[3], 16).toByte()
                            sendMsg.btCmd[12] = Integer.parseInt(strList[4], 16).toByte()
                            sendMsg.btCmd[13] = Integer.parseInt(strList[5], 16).toByte()
                            sendMsg.btCmd[14] = Integer.parseInt(strList[2], 16).toByte()
                            sendMsg.btCmd[15] = Integer.parseInt(strList[0], 16).toByte()
                            sendMsg.btCmd[16] = Integer.parseInt(strList[1], 16).toByte()
                            sendMsg.btCmd[17] = BtDevUnit.ledPwrA6.shr(8).toByte()
                            sendMsg.btCmd[18] = BtDevUnit.ledPwrA6.and(0xff).toByte()
                            sendMsg.btCmd[19] = BtDevUnit.ledMfbA6.shr(8).toByte()
                            sendMsg.btCmd[20] = BtDevUnit.ledMfbA6.and(0xff).toByte()
                            sendMsg.btCmd[21] = BtDevUnit.ledBcbA6.shr(8).toByte()
                            sendMsg.btCmd[22] = BtDevUnit.ledBcbA6.and(0xff).toByte()
                            sendMsg.btCmd[23] = BtDevUnit.ledRevA6.shr(8).toByte()
                            sendMsg.btCmd[24] = BtDevUnit.ledRevA6.and(0xff).toByte()
                            Logger.d(LogGbl, "A6 feature parameter $dev send")
                            Handler().postDelayed({(activity as DevUnitMsg).sendBtServiceMsg(sendMsg)}, (300 * dev).toLong())
                        }
                        "A7" -> {
                            val sendMsg = BtDevMsg(0, 0)

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
                            sendMsg.btCmd[4] = CmdId.SET_HFP_FEATURE_REQ.value
                            sendMsg.btCmd[5] = 0x13
                            sendMsg.btCmd[6] = BtDevUnit.featuerA7.shr(8).toByte()
                            sendMsg.btCmd[7] = BtDevUnit.featuerA7.and(0xff).toByte()
                            sendMsg.btCmd[8] = BtDevUnit.featuerMaxAgNo.toByte()
                            sendMsg.btCmd[9] = BtDevUnit.featureMaxTalkNo.toByte()
                            sendMsg.btCmd[10] = 0x00
                            sendMsg.btCmd[11] = Integer.parseInt(strList[3], 16).toByte()
                            sendMsg.btCmd[12] = Integer.parseInt(strList[4], 16).toByte()
                            sendMsg.btCmd[13] = Integer.parseInt(strList[5], 16).toByte()
                            sendMsg.btCmd[14] = Integer.parseInt(strList[2], 16).toByte()
                            sendMsg.btCmd[15] = Integer.parseInt(strList[0], 16).toByte()
                            sendMsg.btCmd[16] = Integer.parseInt(strList[1], 16).toByte()
                            sendMsg.btCmd[17] = BtDevUnit.ledPwrA7.shr(8).toByte()
                            sendMsg.btCmd[18] = BtDevUnit.ledPwrA7.and(0xff).toByte()
                            sendMsg.btCmd[19] = BtDevUnit.ledMfbA7.shr(8).toByte()
                            sendMsg.btCmd[20] = BtDevUnit.ledMfbA7.and(0xff).toByte()
                            sendMsg.btCmd[21] = BtDevUnit.ledBcbA7.shr(8).toByte()
                            sendMsg.btCmd[22] = BtDevUnit.ledBcbA7.and(0xff).toByte()
                            sendMsg.btCmd[23] = BtDevUnit.ledRevA7.shr(8).toByte()
                            sendMsg.btCmd[24] = BtDevUnit.ledRevA7.and(0xff).toByte()
                            Logger.d(LogGbl, "A7 feature parameter $dev send")
                            Handler().postDelayed({ (activity as DevUnitMsg).sendBtServiceMsg(sendMsg) }, (300 * dev).toLong())
                        }
                        else -> {
                            Logger.d(LogGbl, "other feature parameter $dev send")
                        }
                    }
                }
                when((activity as DevUnitMsg).getDevType(0)) {
                    "M6_SRC" -> {
                        val sendMsg = BtDevMsg(0, 0)
                        val sendAgMsg = BtDevMsg(0, 0)

                        sendAgMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
                        sendAgMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
                        sendAgMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
                        sendAgMsg.btCmd[3] = CmdId.CMD_DEV_AG_ALL.value
                        sendAgMsg.btCmd[4] = CmdId.SET_AG_FEATURE_REQ.value
                        sendAgMsg.btCmd[5] = 0x13
                        sendAgMsg.btCmd[6] = BtDevUnit.featuerM6Ag.shr(8).toByte()
                        sendAgMsg.btCmd[7] = BtDevUnit.featuerM6Ag.and(0xff).toByte()
                        sendMsg.btCmd[8] = BtDevUnit.featuerMaxAgNo.toByte()
                        sendMsg.btCmd[9] = BtDevUnit.featureMaxTalkNo.toByte()
                        sendAgMsg.btCmd[10] = 0x00
                        sendAgMsg.btCmd[11] = Integer.parseInt(strList[3], 16).toByte()
                        sendAgMsg.btCmd[12] = Integer.parseInt(strList[4], 16).toByte()
                        sendAgMsg.btCmd[13] = Integer.parseInt(strList[5], 16).toByte()
                        sendAgMsg.btCmd[14] = Integer.parseInt(strList[2], 16).toByte()
                        sendAgMsg.btCmd[15] = Integer.parseInt(strList[0], 16).toByte()
                        sendAgMsg.btCmd[16] = Integer.parseInt(strList[1], 16).toByte()
                        sendAgMsg.btCmd[17] = BtDevUnit.ledPwrM6Ag.shr(8).toByte()
                        sendAgMsg.btCmd[18] = BtDevUnit.ledPwrM6Ag.and(0xff).toByte()
                        sendAgMsg.btCmd[19] = BtDevUnit.ledMfbM6Ag.shr(8).toByte()
                        sendAgMsg.btCmd[20] = BtDevUnit.ledMfbM6Ag.and(0xff).toByte()
                        sendAgMsg.btCmd[21] = BtDevUnit.ledBcbM6Ag.shr(8).toByte()
                        sendAgMsg.btCmd[22] = BtDevUnit.ledBcbM6Ag.and(0xff).toByte()
                        sendAgMsg.btCmd[23] = BtDevUnit.ledRevM6Ag.shr(8).toByte()
                        sendAgMsg.btCmd[24] = BtDevUnit.ledRevM6Ag.and(0xff).toByte()
                        Logger.d(LogGbl, "M6 AG feature parameter send")
                        Handler().postDelayed({ (activity as DevUnitMsg).sendBtServiceMsg(sendAgMsg) }, (300 * BtDevUnit.deviceNo + 1).toLong())

                        sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
                        sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
                        sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
                        sendMsg.btCmd[3] = CmdId.CMD_DEV_SRC.value
                        sendMsg.btCmd[4] = CmdId.SET_HFP_FEATURE_REQ.value
                        sendMsg.btCmd[5] = 0x13
                        sendMsg.btCmd[6] = BtDevUnit.featuerM6Src.shr(8).toByte()
                        sendMsg.btCmd[7] = BtDevUnit.featuerM6Src.and(0xff).toByte()
                        sendMsg.btCmd[8] = BtDevUnit.featuerMaxAgNo.toByte()
                        sendMsg.btCmd[9] = BtDevUnit.featureMaxTalkNo.toByte()
                        sendMsg.btCmd[10] = 0x00
                        sendMsg.btCmd[11] = Integer.parseInt(strList[3], 16).toByte()
                        sendMsg.btCmd[12] = Integer.parseInt(strList[4], 16).toByte()
                        sendMsg.btCmd[13] = Integer.parseInt(strList[5], 16).toByte()
                        sendMsg.btCmd[14] = Integer.parseInt(strList[2], 16).toByte()
                        sendMsg.btCmd[15] = Integer.parseInt(strList[0], 16).toByte()
                        sendMsg.btCmd[16] = Integer.parseInt(strList[1], 16).toByte()
                        sendMsg.btCmd[17] = BtDevUnit.ledPwrM6Src.shr(8).toByte()
                        sendMsg.btCmd[18] = BtDevUnit.ledPwrM6Src.and(0xff).toByte()
                        sendMsg.btCmd[19] = BtDevUnit.ledMfbM6Src.shr(8).toByte()
                        sendMsg.btCmd[20] = BtDevUnit.ledMfbM6Src.and(0xff).toByte()
                        sendMsg.btCmd[21] = BtDevUnit.ledBcbM6Src.shr(8).toByte()
                        sendMsg.btCmd[22] = BtDevUnit.ledBcbM6Src.and(0xff).toByte()
                        sendMsg.btCmd[23] = BtDevUnit.ledRevM6Src.shr(8).toByte()
                        sendMsg.btCmd[24] = BtDevUnit.ledRevM6Src.and(0xff).toByte()
                        Logger.d(LogGbl, "M6 SRC feature parameter send")
                        Handler().postDelayed({ (activity as DevUnitMsg).sendBtServiceMsg(sendMsg) }, (300 * BtDevUnit.deviceNo + 2).toLong())}
                    "DG_BT" -> {
                        val sendMsg = BtDevMsg(0, 0)

                        sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
                        sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
                        sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
                        sendMsg.btCmd[3] = CmdId.CMD_DEV_SRC.value
                        sendMsg.btCmd[4] = CmdId.SET_HFP_FEATURE_REQ.value
                        sendMsg.btCmd[5] = 0x13
                        sendMsg.btCmd[6] = BtDevUnit.featuerDg.shr(8).toByte()
                        sendMsg.btCmd[7] = BtDevUnit.featuerDg.and(0xff).toByte()
                        sendMsg.btCmd[8] = BtDevUnit.featuerMaxAgNo.toByte()
                        sendMsg.btCmd[9] = BtDevUnit.featureMaxTalkNo.toByte()
                        sendMsg.btCmd[10] = 0x00
                        sendMsg.btCmd[11] = Integer.parseInt(strList[3], 16).toByte()
                        sendMsg.btCmd[12] = Integer.parseInt(strList[4], 16).toByte()
                        sendMsg.btCmd[13] = Integer.parseInt(strList[5], 16).toByte()
                        sendMsg.btCmd[14] = Integer.parseInt(strList[2], 16).toByte()
                        sendMsg.btCmd[15] = Integer.parseInt(strList[0], 16).toByte()
                        sendMsg.btCmd[16] = Integer.parseInt(strList[1], 16).toByte()
                        sendMsg.btCmd[17] = BtDevUnit.ledPwrDg.shr(8).toByte()
                        sendMsg.btCmd[18] = BtDevUnit.ledPwrDg.and(0xff).toByte()
                        sendMsg.btCmd[19] = BtDevUnit.ledMfbDg.shr(8).toByte()
                        sendMsg.btCmd[20] = BtDevUnit.ledMfbDg.and(0xff).toByte()
                        sendMsg.btCmd[21] = BtDevUnit.ledBcbDg.shr(8).toByte()
                        sendMsg.btCmd[22] = BtDevUnit.ledBcbDg.and(0xff).toByte()
                        sendMsg.btCmd[23] = BtDevUnit.ledRevDg.shr(8).toByte()
                        sendMsg.btCmd[24] = BtDevUnit.ledRevDg.and(0xff).toByte()
                        Logger.d(LogGbl, "DG feature parameter send")
                        Handler().postDelayed({ (activity as DevUnitMsg).sendBtServiceMsg(sendMsg) }, (100 * BtDevUnit.deviceNo + 1).toLong())}
                    "VC_BT" -> {
                        val sendMsg = BtDevMsg(0, 0)

                        sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
                        sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
                        sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
                        sendMsg.btCmd[3] = CmdId.CMD_DEV_SRC.value
                        sendMsg.btCmd[4] = CmdId.SET_HFP_FEATURE_REQ.value
                        sendMsg.btCmd[5] = 0x13
                        sendMsg.btCmd[6] = BtDevUnit.featuerVc.shr(8).toByte()
                        sendMsg.btCmd[7] = BtDevUnit.featuerVc.and(0xff).toByte()
                        sendMsg.btCmd[8] = BtDevUnit.featuerMaxAgNo.toByte()
                        sendMsg.btCmd[9] = BtDevUnit.featureMaxTalkNo.toByte()
                        sendMsg.btCmd[10] = 0x00
                        sendMsg.btCmd[11] = Integer.parseInt(strList[3], 16).toByte()
                        sendMsg.btCmd[12] = Integer.parseInt(strList[4], 16).toByte()
                        sendMsg.btCmd[13] = Integer.parseInt(strList[5], 16).toByte()
                        sendMsg.btCmd[14] = Integer.parseInt(strList[2], 16).toByte()
                        sendMsg.btCmd[15] = Integer.parseInt(strList[0], 16).toByte()
                        sendMsg.btCmd[16] = Integer.parseInt(strList[1], 16).toByte()
                        sendMsg.btCmd[17] = BtDevUnit.ledPwrVc.shr(8).toByte()
                        sendMsg.btCmd[18] = BtDevUnit.ledPwrVc.and(0xff).toByte()
                        sendMsg.btCmd[19] = BtDevUnit.ledMfbVc.shr(8).toByte()
                        sendMsg.btCmd[20] = BtDevUnit.ledMfbVc.and(0xff).toByte()
                        sendMsg.btCmd[21] = BtDevUnit.ledBcbVc.shr(8).toByte()
                        sendMsg.btCmd[22] = BtDevUnit.ledBcbVc.and(0xff).toByte()
                        sendMsg.btCmd[23] = BtDevUnit.ledRevVc.shr(8).toByte()
                        sendMsg.btCmd[24] = BtDevUnit.ledRevVc.and(0xff).toByte()
                        Logger.d(LogGbl, "VC feature parameter send")
                        Handler().postDelayed({ (activity as DevUnitMsg).sendBtServiceMsg(sendMsg) }, (100 * BtDevUnit.deviceNo + 1).toLong())}
                    else -> {
                        Logger.d(LogGbl, "other source feature parameter send")
                    }
                }
            }.setNegativeButton(R.string.txvCancel) { _, _ ->
            }.show()
            true
        }

        btnFeatureDfu.setOnClickListener {
            val sendMsg = BtDevMsg(0, 0)
            val sendMsgOld = BtDevMsg(0, 0)

            AlertDialog.Builder(activity).setTitle(R.string.txvDevDuf).setPositiveButton(R.string.txvOk) { _, _ ->
                sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
                sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
                sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
                sendMsg.btCmd[3] =
                    when(rdGpDevFeature.checkedRadioButtonId) {
                        R.id.rdSrcFeature -> 0x30
                        R.id.rdAgAllFeature -> 0x38
                        R.id.rdHfpAllFeature -> 0x38
                        else -> 0x38
                    }
                sendMsg.btCmd[4] =
                    when(rdGpDevFeature.checkedRadioButtonId) {
                        R.id.rdSrcFeature -> CmdId.SET_HFP_CTRL_REQ.value
                        R.id.rdAgAllFeature -> CmdId.SET_AG_CTRL_REQ.value
                        R.id.rdHfpAllFeature -> CmdId.SET_HFP_CTRL_REQ.value
                        else -> CmdId.SET_HFP_PSKEY_REQ.value
                    }
                sendMsg.btCmd[5] = 0x02
                sendMsg.btCmd[6] = 0x04
                sendMsg.btCmd[7] = 0x01
                (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)

                sendMsgOld.btCmd[0] = CmdId.CMD_HEAD_FF.value
                sendMsgOld.btCmd[1] = CmdId.CMD_HEAD_55.value
                sendMsgOld.btCmd[2] = CmdId.CMD_DEV_HOST.value
                sendMsgOld.btCmd[3] =
                    when(rdGpDevFeature.checkedRadioButtonId) {
                        R.id.rdSrcFeature -> 0x30
                        R.id.rdAgAllFeature -> 0x38
                        R.id.rdHfpAllFeature -> 0x38
                        else -> 0x38
                    }
                sendMsgOld.btCmd[4] =
                    when(rdGpDevFeature.checkedRadioButtonId) {
                        R.id.rdSrcFeature -> CmdId.SET_HFP_DFU_REQ.value
                        R.id.rdAgAllFeature -> CmdId.SET_AG_DFU_REQ.value
                        R.id.rdHfpAllFeature -> CmdId.SET_HFP_DFU_REQ.value
                        else -> CmdId.SET_HFP_DFU_REQ.value
                    }
                sendMsgOld.btCmd[5] = 0x00
                (activity as DevUnitMsg).sendBtServiceMsg(sendMsgOld)
            }.setNegativeButton(R.string.txvCancel) { _, _ ->
            }.show()
        }

        btnFeatureDfu.setOnLongClickListener {
            val sendMsg = BtDevMsg(0, 0)

            AlertDialog.Builder(activity).setTitle(R.string.txvDevReset).setPositiveButton(R.string.txvOk) { _, _ ->
                sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
                sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
                sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
                sendMsg.btCmd[3] =
                    when(rdGpDevFeature.checkedRadioButtonId) {
                    R.id.rdSrcFeature -> 0x30
                    R.id.rdAgAllFeature -> 0x38
                    R.id.rdHfpAllFeature -> 0x38
                    else -> 0x38
                }
                sendMsg.btCmd[4] =
                    when(rdGpDevFeature.checkedRadioButtonId) {
                        R.id.rdSrcFeature -> CmdId.SET_HFP_CTRL_REQ.value
                        R.id.rdAgAllFeature -> CmdId.SET_AG_CTRL_REQ.value
                        R.id.rdHfpAllFeature -> CmdId.SET_HFP_CTRL_REQ.value
                        else -> CmdId.SET_HFP_PSKEY_REQ.value
                    }
                sendMsg.btCmd[5] = 0x02
                sendMsg.btCmd[6] = 0x02
                sendMsg.btCmd[7] = 0x01
                (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
            }.setNegativeButton(R.string.txvCancel) { _, _ ->
            }.show()
            true
        }

        rdGpDevFeature.setOnCheckedChangeListener { _, _ ->
            deviceItemGet()
            updateData()
        }

        chkFeature0.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isCheckReady == true)
                checkUpdate(buttonView, isChecked)
        }
        chkFeature1.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isCheckReady == true)
                checkUpdate(buttonView, isChecked)
        }
        chkFeature2.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isCheckReady == true)
                checkUpdate(buttonView, isChecked)
        }
        chkFeature3.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isCheckReady == true)
                checkUpdate(buttonView, isChecked)
        }
        chkFeature4.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isCheckReady == true)
                checkUpdate(buttonView, isChecked)
        }
        chkFeature5.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isCheckReady == true)
                checkUpdate(buttonView, isChecked)
        }
        chkFeature6.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isCheckReady == true)
                checkUpdate(buttonView, isChecked)
        }
        chkFeature7.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isCheckReady == true)
                checkUpdate(buttonView, isChecked)
        }
        chkFeature8.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isCheckReady == true)
                checkUpdate(buttonView, isChecked)
        }
        chkFeature9.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isCheckReady == true)
                checkUpdate(buttonView, isChecked)
        }
        chkFeature10.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isCheckReady == true)
                checkUpdate(buttonView, isChecked)
        }
        chkFeature11.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isCheckReady == true)
                checkUpdate(buttonView, isChecked)
        }
        chkFeature12.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isCheckReady == true)
                checkUpdate(buttonView, isChecked)
        }
        chkFeature13.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isCheckReady == true)
                checkUpdate(buttonView, isChecked)
        }
        chkFeature14.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isCheckReady == true)
                checkUpdate(buttonView, isChecked)
        }
        chkFeature15.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isCheckReady == true)
                checkUpdate(buttonView, isChecked)
        }
        seekLedPwr.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                deviceItemGet()
                if (rdGpDevFeature.checkedRadioButtonId == R.id.rdAgAllFeature) {
                    (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].ledLightAg[0] = seekLedPwr.progress
                } else {
                    (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].ledLightHfp[0] = seekLedPwr.progress
                }
            }
        })
        seekLedMfb.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                deviceItemGet()
                if (rdGpDevFeature.checkedRadioButtonId == R.id.rdAgAllFeature) {
                    (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].ledLightAg[1] = seekLedMfb.progress
                } else {
                    (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].ledLightHfp[1] = seekLedMfb.progress
                }
            }
        })
        seekLedBcb.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                deviceItemGet()
                if (rdGpDevFeature.checkedRadioButtonId == R.id.rdAgAllFeature) {
                    (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].ledLightAg[2] = seekLedBcb.progress
                } else {
                    (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].ledLightHfp[2] = seekLedBcb.progress
                }
            }
        })
        seekLedRev.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                deviceItemGet()
                if (rdGpDevFeature.checkedRadioButtonId == R.id.rdAgAllFeature) {
                    (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].ledLightAg[3] = seekLedRev.progress
                } else {
                    (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].ledLightHfp[3] = seekLedRev.progress
                }
            }
        })
        updateData()
    }

    override fun onPause() {
        super.onPause()

        Logger.d(LogGbl, "FragmentConState on Pause")
        isFragmentReady = false
        isCheckReady = false
    }

    fun deviceItemGet() {
        srcDevItem =
            when(rdGpDevFeature.checkedRadioButtonId) {
                R.id.rdSrcFeature -> 0
                R.id.rdAgAllFeature -> 1
                R.id.rdHfpAllFeature -> {
                    var devItem = 1

                    for (dev in 1..BtDevUnit.deviceNo) {
                        if ((activity as DevUnitMsg).getBtDevUnitList()[dev].stateCon.and(0x00000220) == 0x00000220) {
                            devItem = dev
                            break
                        }
                    }
                    devItem
                }
                else -> 0
            }
    }

    fun checkUpdate(it: View, isChecked: Boolean) {
        val i =
            when(it.id) {
                R.id.chkFeature0 -> 0
                R.id.chkFeature1 -> 1
                R.id.chkFeature2 -> 2
                R.id.chkFeature3 -> 3
                R.id.chkFeature4 -> 4
                R.id.chkFeature5 -> 5
                R.id.chkFeature6 -> 6
                R.id.chkFeature7 -> 7
                R.id.chkFeature8 -> 8
                R.id.chkFeature9 -> 9
                R.id.chkFeature10 -> 10
                R.id.chkFeature11 -> 11
                R.id.chkFeature12 -> 12
                R.id.chkFeature13 -> 13
                R.id.chkFeature14 -> 14
                R.id.chkFeature15 -> 15
                else -> 16
            }

        if(rdGpDevFeature.checkedRadioButtonId == R.id.rdAgAllFeature) {
            (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].featureAg =
                if (isChecked)
                    (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].featureAg.or(1.shl(i))
                else
                    (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].featureAg.and(1.shl(i).inv())
            Logger.d(LogGbl, String.format("checkbox id:%2d feature:%4X", i, (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].featureAg))
        }
        else {
            (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].featureHfp =
                if (isChecked)
                    (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].featureHfp.or(1.shl(i))
                else
                    (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].featureHfp.and(1.shl(i).inv())
            Logger.d(LogGbl, String.format("checkbox id:%2d feature:%4X", i, (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].featureHfp))
        }
    }

    fun updateData() {
        var chkBox: CheckBox
        var feature: Int
        var ledLight: Array<Int>

        if(isFragmentReady == false) {
            Handler().postDelayed({updateData()}, 100)
            Logger.d(LogGbl, "fragment not ready")
            return
        }

        Handler().postDelayed({ isCheckReady = true}, 200)
        if (rdGpDevFeature.checkedRadioButtonId == R.id.rdAgAllFeature) {
            feature = (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].featureAg
            ledLight = (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].ledLightAg
            txvFilterBda.text =
                (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].bdaddrFilterAg
        } else {
            feature = (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].featureHfp
            ledLight = (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].ledLightHfp
            txvFilterBda.text =
                (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].bdaddrFilterHfp
        }

        when ((activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].featureMode.and(0x0f000000)) {
            0x01000000 -> rdModeUsbFeature.isChecked = true
            0x02000000 -> rdModeBtFeature.isChecked = true
            0x04000000 -> rdModeVcsFeature.isChecked = true
            0x08000000 -> rdModeWireFeature.isChecked = true
        }
        for (i in 0..15) {
            chkBox =
                when (i) {
                    0 -> chkFeature0
                    1 -> chkFeature1
                    2 -> chkFeature2
                    3 -> chkFeature3
                    4 -> chkFeature4
                    5 -> chkFeature5
                    6 -> chkFeature6
                    7 -> chkFeature7
                    8 -> chkFeature8
                    9 -> chkFeature9
                    10 -> chkFeature10
                    11 -> chkFeature11
                    12 -> chkFeature12
                    13 -> chkFeature13
                    14 -> chkFeature14
                    15 -> chkFeature15
                    else -> chkFeature0
                }
            chkBox.isChecked =
                if (feature.and(1.shl(i)) == 1.shl(i))
                    true
                else
                    false
        }
        seekLedPwr.progress = ledLight[0]
        seekLedMfb.progress = ledLight[1]
        seekLedBcb.progress = ledLight[2]
        seekLedRev.progress = ledLight[3]
        when (BtDevUnit.sppStateCon) {
            0x00.toByte() -> {
                btnFeatureWrite.visibility = View.VISIBLE
                btnFeatureRead.visibility = View.VISIBLE
                btnFeatureDfu.visibility = View.VISIBLE
            }
            0x01.toByte() -> {
                btnFeatureWrite.visibility = View.INVISIBLE
                btnFeatureRead.visibility = View.INVISIBLE
                btnFeatureDfu.visibility = View.INVISIBLE
            }
            else -> {
                btnFeatureWrite.visibility = View.INVISIBLE
                btnFeatureRead.visibility = View.INVISIBLE
                btnFeatureDfu.visibility = View.INVISIBLE
            }
        }
    }
}
