package com.example.imagebtapp_v001b001

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_feature_set.*
import kotlinx.android.synthetic.main.fragment_rf_test.*

class FragmentRfTest : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.d(LogGbl, "FragmentRfTest on Create")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rf_test, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btnRfTestHfp0.setOnClickListener {
            val sendMsg = BtDevMsg(0, 0)

            sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[3] = 0x00
            sendMsg.btCmd[4] = CmdId.SET_HFP_TEST_REQ.value
            sendMsg.btCmd[5] = 0x01
            sendMsg.btCmd[6] = 0x12
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
        }
        btnRfTestHfp0.setOnLongClickListener {
            val sendMsg = BtDevMsg(0, 0)

            sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[3] = 0x00
            sendMsg.btCmd[4] = CmdId.SET_HFP_TEST_REQ.value
            sendMsg.btCmd[5] = 0x01
            sendMsg.btCmd[6] = 0x00
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
            true
        }
        btnRfTestHfp1.setOnClickListener {
            val sendMsg = BtDevMsg(0, 0)

            sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[3] = 0x08
            sendMsg.btCmd[4] = CmdId.SET_HFP_TEST_REQ.value
            sendMsg.btCmd[5] = 0x01
            sendMsg.btCmd[6] = 0x12
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
        }
        btnRfTestHfp1.setOnLongClickListener {
            val sendMsg = BtDevMsg(0, 0)

            sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[3] = 0x08
            sendMsg.btCmd[4] = CmdId.SET_HFP_TEST_REQ.value
            sendMsg.btCmd[5] = 0x01
            sendMsg.btCmd[6] = 0x00
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
            true
        }
        btnRfTestHfp2.setOnClickListener {
            val sendMsg = BtDevMsg(0, 0)

            sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[3] = 0x10
            sendMsg.btCmd[4] = CmdId.SET_HFP_TEST_REQ.value
            sendMsg.btCmd[5] = 0x01
            sendMsg.btCmd[6] = 0x12
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
        }
        btnRfTestHfp2.setOnLongClickListener {
            val sendMsg = BtDevMsg(0, 0)

            sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[3] = 0x10
            sendMsg.btCmd[4] = CmdId.SET_HFP_TEST_REQ.value
            sendMsg.btCmd[5] = 0x01
            sendMsg.btCmd[6] = 0x00
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
            true
        }
        btnRfTestHfp3.setOnClickListener {
            val sendMsg = BtDevMsg(0, 0)

            sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[3] = 0x18
            sendMsg.btCmd[4] = CmdId.SET_HFP_TEST_REQ.value
            sendMsg.btCmd[5] = 0x01
            sendMsg.btCmd[6] = 0x12
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
        }
        btnRfTestHfp3.setOnLongClickListener {
            val sendMsg = BtDevMsg(0, 0)

            sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[3] = 0x18
            sendMsg.btCmd[4] = CmdId.SET_HFP_TEST_REQ.value
            sendMsg.btCmd[5] = 0x01
            sendMsg.btCmd[6] = 0x00
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
            true
        }
        btnRfTestHfp4.setOnClickListener {
            val sendMsg = BtDevMsg(0, 0)

            sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[3] = 0x20
            sendMsg.btCmd[4] = CmdId.SET_HFP_TEST_REQ.value
            sendMsg.btCmd[5] = 0x01
            sendMsg.btCmd[6] = 0x12
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
        }
        btnRfTestHfp4.setOnLongClickListener {
            val sendMsg = BtDevMsg(0, 0)

            sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[3] = 0x20
            sendMsg.btCmd[4] = CmdId.SET_HFP_TEST_REQ.value
            sendMsg.btCmd[5] = 0x01
            sendMsg.btCmd[6] = 0x00
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
            true
        }
        btnRfTestHfp5.setOnClickListener {
            val sendMsg = BtDevMsg(0, 0)

            sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[3] = 0x28
            sendMsg.btCmd[4] = CmdId.SET_HFP_TEST_REQ.value
            sendMsg.btCmd[5] = 0x01
            sendMsg.btCmd[6] = 0x12
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
        }
        btnRfTestHfp5.setOnLongClickListener {
            val sendMsg = BtDevMsg(0, 0)

            sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[3] = 0x28
            sendMsg.btCmd[4] = CmdId.SET_HFP_TEST_REQ.value
            sendMsg.btCmd[5] = 0x01
            sendMsg.btCmd[6] = 0x00
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
            true
        }
        btnRfTestSrc.setOnClickListener {
            val sendMsg = BtDevMsg(0, 0)

            sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[3] = 0x30
            sendMsg.btCmd[4] = CmdId.SET_HFP_TEST_REQ.value
            sendMsg.btCmd[5] = 0x01
            sendMsg.btCmd[6] = 0x12
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
        }
        btnRfTestSrc.setOnLongClickListener {
            val sendMsg = BtDevMsg(0, 0)

            sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[3] = 0x30
            sendMsg.btCmd[4] = CmdId.SET_HFP_TEST_REQ.value
            sendMsg.btCmd[5] = 0x01
            sendMsg.btCmd[6] = 0x00
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
            true
        }
        btnRfTestAg0.setOnClickListener {
            val sendMsg = BtDevMsg(0, 0)

            sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[3] = 0x00
            sendMsg.btCmd[4] = CmdId.SET_AG_TEST_REQ.value
            sendMsg.btCmd[5] = 0x01
            sendMsg.btCmd[6] = 0x12
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
        }
        btnRfTestAg0.setOnLongClickListener {
            val sendMsg = BtDevMsg(0, 0)

            sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[3] = 0x00
            sendMsg.btCmd[4] = CmdId.SET_AG_TEST_REQ.value
            sendMsg.btCmd[5] = 0x01
            sendMsg.btCmd[6] = 0x00
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
            true
        }
        btnRfTestAg1.setOnClickListener {
            val sendMsg = BtDevMsg(0, 0)

            sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[3] = 0x10
            sendMsg.btCmd[4] = CmdId.SET_AG_TEST_REQ.value
            sendMsg.btCmd[5] = 0x01
            sendMsg.btCmd[6] = 0x12
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
        }
        btnRfTestAg1.setOnLongClickListener {
            val sendMsg = BtDevMsg(0, 0)

            sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[3] = 0x10
            sendMsg.btCmd[4] = CmdId.SET_AG_TEST_REQ.value
            sendMsg.btCmd[5] = 0x01
            sendMsg.btCmd[6] = 0x00
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
            true
        }
        btnRfTestAg2.setOnClickListener {
            val sendMsg = BtDevMsg(0, 0)

            sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[3] = 0x20
            sendMsg.btCmd[4] = CmdId.SET_AG_TEST_REQ.value
            sendMsg.btCmd[5] = 0x01
            sendMsg.btCmd[6] = 0x12
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
        }
        btnRfTestAg2.setOnLongClickListener {
            val sendMsg = BtDevMsg(0, 0)

            sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[3] = 0x20
            sendMsg.btCmd[4] = CmdId.SET_AG_TEST_REQ.value
            sendMsg.btCmd[5] = 0x01
            sendMsg.btCmd[6] = 0x00
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
            true
        }
    }

    fun updateData() {
    }
}