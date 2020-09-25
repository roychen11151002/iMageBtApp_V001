package com.example.imagebtapp_v001b001

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_rf_test.*
import kotlin.experimental.or

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
        var rfTestTime = (3 + 1).shl(2)
        var rfTestMode: Byte = 0x02
        var txPower = 0

        txvRfTestShow.text = String.format("%s(%03d)", context!!.resources.getString(R.string.txvRfTestTime), ((rfTestTime * 5).shr(2)))
        rdGpRfTestFreq.setOnCheckedChangeListener { _, checkedId ->
            rfTestMode =
                when (checkedId) {
                    R.id.rdRfTestFreqHi -> 3
                    R.id.rdRfTestFreqMid -> 2
                    R.id.rdRfTestFreqLo -> 1
                    else -> 0
                }
        }

        seekRfTestTime.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                rfTestTime = (progress + 1).shl(2)
                txvRfTestShow.text = String.format("%s(%03d)", context!!.resources.getString(R.string.txvRfTestTime), ((rfTestTime * 5).shr(2)))
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        btnRfTestHfp0.setOnClickListener {
            val sendMsg = BtDevMsg(0, 0)

            sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[3] = 0x00
            sendMsg.btCmd[4] = CmdId.SET_HFP_CTRL_REQ.value
            sendMsg.btCmd[5] = 0x02
            sendMsg.btCmd[6] = 0x08
            sendMsg.btCmd[7] = rfTestMode.or(rfTestTime.toByte())
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
        }

        btnRfTestHfp0.setOnLongClickListener {
            val sendMsg = BtDevMsg(0, 0)

            sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[3] = 0x00
            sendMsg.btCmd[4] = CmdId.SET_HFP_CTRL_REQ.value
            sendMsg.btCmd[5] = 0x02
            sendMsg.btCmd[6] = 0x08
            sendMsg.btCmd[7] = 0x00
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
            true
        }

        btnRfTestHfp1.setOnClickListener {
            val sendMsg = BtDevMsg(0, 0)

            sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[3] = 0x08
            sendMsg.btCmd[4] = CmdId.SET_HFP_CTRL_REQ.value
            sendMsg.btCmd[5] = 0x02
            sendMsg.btCmd[6] = 0x08
            sendMsg.btCmd[7] = rfTestMode.or(rfTestTime.toByte())
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
        }

        btnRfTestHfp1.setOnLongClickListener {
            val sendMsg = BtDevMsg(0, 0)

            sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[3] = 0x08
            sendMsg.btCmd[4] = CmdId.SET_HFP_CTRL_REQ.value
            sendMsg.btCmd[5] = 0x02
            sendMsg.btCmd[6] = 0x08
            sendMsg.btCmd[7] = 0x00
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
            true
        }

        btnRfTestHfp2.setOnClickListener {
            val sendMsg = BtDevMsg(0, 0)

            sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[3] = 0x10
            sendMsg.btCmd[4] = CmdId.SET_HFP_CTRL_REQ.value
            sendMsg.btCmd[5] = 0x02
            sendMsg.btCmd[6] = 0x08
            sendMsg.btCmd[7] = rfTestMode.or(rfTestTime.toByte())
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
        }

        btnRfTestHfp2.setOnLongClickListener {
            val sendMsg = BtDevMsg(0, 0)

            sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[3] = 0x10
            sendMsg.btCmd[4] = CmdId.SET_HFP_CTRL_REQ.value
            sendMsg.btCmd[5] = 0x02
            sendMsg.btCmd[6] = 0x08
            sendMsg.btCmd[7] = 0x00
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
            true
        }

        btnRfTestHfp3.setOnClickListener {
            val sendMsg = BtDevMsg(0, 0)

            sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[3] = 0x18
            sendMsg.btCmd[4] = CmdId.SET_HFP_CTRL_REQ.value
            sendMsg.btCmd[5] = 0x02
            sendMsg.btCmd[6] = 0x08
            sendMsg.btCmd[7] = rfTestMode.or(rfTestTime.toByte())
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
        }

        btnRfTestHfp3.setOnLongClickListener {
            val sendMsg = BtDevMsg(0, 0)

            sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[3] = 0x18
            sendMsg.btCmd[4] = CmdId.SET_HFP_CTRL_REQ.value
            sendMsg.btCmd[5] = 0x02
            sendMsg.btCmd[6] = 0x08
            sendMsg.btCmd[7] = 0x00
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
            true
        }

        btnRfTestHfp4.setOnClickListener {
            val sendMsg = BtDevMsg(0, 0)

            sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[3] = 0x20
            sendMsg.btCmd[4] = CmdId.SET_HFP_CTRL_REQ.value
            sendMsg.btCmd[5] = 0x02
            sendMsg.btCmd[6] = 0x08
            sendMsg.btCmd[7] = rfTestMode.or(rfTestTime.toByte())
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
        }

        btnRfTestHfp4.setOnLongClickListener {
            val sendMsg = BtDevMsg(0, 0)

            sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[3] = 0x20
            sendMsg.btCmd[4] = CmdId.SET_HFP_CTRL_REQ.value
            sendMsg.btCmd[5] = 0x02
            sendMsg.btCmd[6] = 0x08
            sendMsg.btCmd[7] = 0x00
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
            true
        }

        btnRfTestHfp5.setOnClickListener {
            val sendMsg = BtDevMsg(0, 0)

            sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[3] = 0x28
            sendMsg.btCmd[4] = CmdId.SET_HFP_CTRL_REQ.value
            sendMsg.btCmd[5] = 0x02
            sendMsg.btCmd[6] = 0x08
            sendMsg.btCmd[7] = rfTestMode.or(rfTestTime.toByte())
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
        }

        btnRfTestHfp5.setOnLongClickListener {
            val sendMsg = BtDevMsg(0, 0)

            sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[3] = 0x28
            sendMsg.btCmd[4] = CmdId.SET_HFP_CTRL_REQ.value
            sendMsg.btCmd[5] = 0x02
            sendMsg.btCmd[6] = 0x08
            sendMsg.btCmd[7] = 0x00
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
            true
        }

        btnRfTestSrc.setOnClickListener {
            val sendMsg = BtDevMsg(0, 0)

            sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[3] = 0x30
            sendMsg.btCmd[4] = CmdId.SET_HFP_CTRL_REQ.value
            sendMsg.btCmd[5] = 0x02
            sendMsg.btCmd[6] = 0x08
            sendMsg.btCmd[7] = rfTestMode.or(rfTestTime.toByte())
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
        }

        btnRfTestSrc.setOnLongClickListener {
            val sendMsg = BtDevMsg(0, 0)

            sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[3] = 0x30
            sendMsg.btCmd[4] = CmdId.SET_HFP_CTRL_REQ.value
            sendMsg.btCmd[5] = 0x02
            sendMsg.btCmd[6] = 0x08
            sendMsg.btCmd[7] = 0x00
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
            true
        }

        btnRfTestAg0.setOnClickListener {
            val sendMsg = BtDevMsg(0, 0)

            sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[3] = 0x00
            sendMsg.btCmd[4] = CmdId.SET_AG_CTRL_REQ.value
            sendMsg.btCmd[5] = 0x02
            sendMsg.btCmd[6] = 0x08
            sendMsg.btCmd[6] = rfTestMode.or(rfTestTime.toByte())
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
        }

        btnRfTestAg0.setOnLongClickListener {
            val sendMsg = BtDevMsg(0, 0)

            sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[3] = 0x00
            sendMsg.btCmd[4] = CmdId.SET_AG_CTRL_REQ.value
            sendMsg.btCmd[5] = 0x02
            sendMsg.btCmd[6] = 0x08
            sendMsg.btCmd[7] = 0x00
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
            true
        }

        btnRfTestAg1.setOnClickListener {
            val sendMsg = BtDevMsg(0, 0)

            sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[3] = 0x10
            sendMsg.btCmd[4] = CmdId.SET_AG_CTRL_REQ.value
            sendMsg.btCmd[5] = 0x02
            sendMsg.btCmd[6] = 0x08
            sendMsg.btCmd[7] = rfTestMode.or(rfTestTime.toByte())
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
        }

        btnRfTestAg1.setOnLongClickListener {
            val sendMsg = BtDevMsg(0, 0)

            sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[3] = 0x10
            sendMsg.btCmd[4] = CmdId.SET_AG_CTRL_REQ.value
            sendMsg.btCmd[5] = 0x02
            sendMsg.btCmd[6] = 0x08
            sendMsg.btCmd[7] = 0x00
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
            true
        }

        btnRfTestAg2.setOnClickListener {
            val sendMsg = BtDevMsg(0, 0)

            sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[3] = 0x20
            sendMsg.btCmd[4] = CmdId.SET_AG_CTRL_REQ.value
            sendMsg.btCmd[5] = 0x02
            sendMsg.btCmd[6] = 0x08
            sendMsg.btCmd[7] = rfTestMode.or(rfTestTime.toByte())
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
        }

        btnRfTestAg2.setOnLongClickListener {
            val sendMsg = BtDevMsg(0, 0)

            sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[3] = 0x20
            sendMsg.btCmd[4] = CmdId.SET_AG_CTRL_REQ.value
            sendMsg.btCmd[5] = 0x02
            sendMsg.btCmd[6] = 0x08
            sendMsg.btCmd[7] = 0x00
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
            true
        }
    }

    fun updateData() {
        if (BtDevUnit.sppStateCon == 0x00.toByte()) {
            btnRfTestSrc.visibility = View.VISIBLE
            btnRfTestAg0.visibility = View.VISIBLE
            btnRfTestAg1.visibility = View.VISIBLE
            btnRfTestAg2.visibility = View.VISIBLE
            btnRfTestHfp0.visibility = View.VISIBLE
            btnRfTestHfp1.visibility = View.VISIBLE
            btnRfTestHfp2.visibility = View.VISIBLE
            btnRfTestHfp3.visibility = View.VISIBLE
            btnRfTestHfp4.visibility = View.VISIBLE
            btnRfTestHfp5.visibility = View.VISIBLE
        }
        else {
            btnRfTestSrc.visibility = View.INVISIBLE
            btnRfTestAg0.visibility = View.INVISIBLE
            btnRfTestAg1.visibility = View.INVISIBLE
            btnRfTestAg2.visibility = View.INVISIBLE
            btnRfTestHfp0.visibility = View.INVISIBLE
            btnRfTestHfp1.visibility = View.INVISIBLE
            btnRfTestHfp2.visibility = View.INVISIBLE
            btnRfTestHfp3.visibility = View.INVISIBLE
            btnRfTestHfp4.visibility = View.INVISIBLE
            btnRfTestHfp5.visibility = View.INVISIBLE
        }
    }
}