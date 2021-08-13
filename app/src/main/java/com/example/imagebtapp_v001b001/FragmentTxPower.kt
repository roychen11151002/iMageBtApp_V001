package com.example.imagebtapp_v001b001

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_tx_power.*

class FragmentTxPower : Fragment() {
    private var isFragmentReady = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.d(LogGbl, "FragmentTxPower on Create")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tx_power, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var txPowerSrc = 0
        var txPowerAg0 = 0
        var txPowerAg1 = 0
        var txPowerAg2 = 0

        isFragmentReady = true
        seekTxPowerSrc.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                txPowerSrc = progress * 4 - 20
                (activity as DevUnitMsg).getBtDevUnitList()[0].txPowerHfp = txPowerSrc
                txvDistanceSrc.text = String.format("%s(%d)", context?.resources?.getString(R.string.txvDistanceSrc), txPowerSrc)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        seekTxPowerAg0.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                txPowerAg0 = progress * 4 - 20
                (activity as DevUnitMsg).getBtDevUnitList()[1].txPowerAg = txPowerAg0
                txvDistanceAg0.text = String.format("%s(%d)", context?.resources?.getString(R.string.txvDistanceAg0), txPowerAg0)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        seekTxPowerAg1.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                txPowerAg1 = progress * 4 - 20
                (activity as DevUnitMsg).getBtDevUnitList()[3].txPowerAg = txPowerAg1
                txvDistanceAg1.text = String.format("%s(%d)", context?.resources?.getString(R.string.txvDistanceAg1), txPowerAg1)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        seekTxPowerAg2.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                txPowerAg2 = progress * 4 - 20
                (activity as DevUnitMsg).getBtDevUnitList()[5].txPowerAg = txPowerAg2
                txvDistanceAg2.text = String.format("%s(%d)", context?.resources?.getString(R.string.txvDistanceAg2), txPowerAg2)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        btnTxPwrWriteAll.setOnClickListener {
            val sendMsgSrc = BtDevMsg(0, 0)

            (activity as DevUnitMsg).setVibrator(200)
            sendMsgSrc.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsgSrc.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsgSrc.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsgSrc.btCmd[3] = 0x30
            sendMsgSrc.btCmd[4] = CmdId.SET_HFP_CTRL_REQ.value
            sendMsgSrc.btCmd[5] = 0x02
            sendMsgSrc.btCmd[6] = 0x10
            sendMsgSrc.btCmd[7] = (seekTxPowerSrc.progress * 4 - 20).toByte()
            Handler().postDelayed({(activity as DevUnitMsg).sendBtServiceMsg(sendMsgSrc)}, 500)
            (activity as DevUnitMsg).getBtDevUnitList()[0].txPowerHfp = txPowerSrc

            for(dev: Int in 1 until (activity as DevUnitMsg).getBtDevUnitList().size step 2) {
                val sendMsgAg = BtDevMsg(0, 0)

                sendMsgAg.btCmd[0] = CmdId.CMD_HEAD_FF.value
                sendMsgAg.btCmd[1] = CmdId.CMD_HEAD_55.value
                sendMsgAg.btCmd[2] = CmdId.CMD_DEV_HOST.value
                sendMsgAg.btCmd[3] =
                    when(dev) {
                        1 -> 0x00
                        3 -> 0x10
                        5 -> 0x20
                        else -> 0x00
                    }
                sendMsgAg.btCmd[4] = CmdId.SET_AG_CTRL_REQ.value
                sendMsgAg.btCmd[5] = 0x02
                sendMsgAg.btCmd[6] = 0x10
                sendMsgAg.btCmd[7] =
                    when(dev) {
                        1 -> {
                            (activity as DevUnitMsg).getBtDevUnitList()[1].txPowerAg = txPowerAg0
                            (seekTxPowerAg0.progress * 4 - 20).toByte()
                        }
                        3 -> {
                            (activity as DevUnitMsg).getBtDevUnitList()[3].txPowerAg = txPowerAg1
                            (seekTxPowerAg1.progress * 4 - 20).toByte()
                        }
                        5 -> {
                            (activity as DevUnitMsg).getBtDevUnitList()[5].txPowerAg = txPowerAg2
                            (seekTxPowerAg2.progress * 4 - 20).toByte()
                        }
                        else -> {
                            (activity as DevUnitMsg).getBtDevUnitList()[1].txPowerAg = txPowerAg0
                            (seekTxPowerAg0.progress * 4 - 20).toByte()
                        }
                    }
                (activity as DevUnitMsg).sendBtServiceMsg(sendMsgAg)
            }
        }

        btnTxPwrWriteSrc.setOnClickListener {
            val sendMsgSrc = BtDevMsg(0, 0)

            (activity as DevUnitMsg).setVibrator(200)
            sendMsgSrc.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsgSrc.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsgSrc.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsgSrc.btCmd[3] = 0x30
            sendMsgSrc.btCmd[4] = CmdId.SET_HFP_CTRL_REQ.value
            sendMsgSrc.btCmd[5] = 0x02
            sendMsgSrc.btCmd[6] = 0x10
            sendMsgSrc.btCmd[7] = (seekTxPowerSrc.progress * 4 - 20).toByte()
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsgSrc)
            (activity as DevUnitMsg).getBtDevUnitList()[0].txPowerHfp = txPowerSrc
        }

        btnTxPwrWriteAg0.setOnClickListener {
            val sendMsgSrc = BtDevMsg(0, 0)
            val sendMsgAg = BtDevMsg(0, 0)

            (activity as DevUnitMsg).setVibrator(200)
            sendMsgSrc.btCmd[0] = CmdId.CMD_HEAD_FF.value               // set AG ID
            sendMsgSrc.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsgSrc.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsgSrc.btCmd[3] = CmdId.CMD_DEV_SRC.value
            sendMsgSrc.btCmd[4] = CmdId.GET_SRC_DEV_NO_REQ.value
            sendMsgSrc.btCmd[5] = 0x01
            sendMsgSrc.btCmd[6] = 0x00
            Handler().postDelayed({ (activity as DevUnitMsg).sendBtServiceMsg(sendMsgSrc)}, 2000)

            sendMsgAg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsgAg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsgAg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsgAg.btCmd[3] = 0x00
            sendMsgAg.btCmd[4] = CmdId.SET_AG_CTRL_REQ.value
            sendMsgAg.btCmd[5] = 0x02
            sendMsgAg.btCmd[6] = 0x10
            sendMsgAg.btCmd[7] = (seekTxPowerAg0.progress * 4 - 20).toByte()
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsgAg)
            (activity as DevUnitMsg).getBtDevUnitList()[1].txPowerAg = txPowerAg0
        }

        btnTxPwrWriteAg1.setOnClickListener {
            val sendMsgSrc = BtDevMsg(0, 0)
            val sendMsgAg = BtDevMsg(0, 0)

            (activity as DevUnitMsg).setVibrator(200)
            sendMsgSrc.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsgSrc.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsgSrc.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsgSrc.btCmd[3] = CmdId.CMD_DEV_SRC.value
            sendMsgSrc.btCmd[4] = CmdId.GET_SRC_DEV_NO_REQ.value
            sendMsgSrc.btCmd[5] = 0x01
            sendMsgSrc.btCmd[6] = 0x00
            Handler().postDelayed({ (activity as DevUnitMsg).sendBtServiceMsg(sendMsgSrc)}, 2000)

            sendMsgAg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsgAg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsgAg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsgAg.btCmd[3] = 0x10
            sendMsgAg.btCmd[4] = CmdId.SET_AG_CTRL_REQ.value
            sendMsgAg.btCmd[5] = 0x02
            sendMsgAg.btCmd[6] = 0x10
            sendMsgAg.btCmd[7] = (seekTxPowerAg1.progress * 4 - 20).toByte()
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsgAg)
            (activity as DevUnitMsg).getBtDevUnitList()[3].txPowerAg = txPowerAg1
        }

        btnTxPwrWriteAg2.setOnClickListener {
            val sendMsgSrc = BtDevMsg(0, 0)
            val sendMsgAg = BtDevMsg(0, 0)

            (activity as DevUnitMsg).setVibrator(200)
            sendMsgSrc.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsgSrc.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsgSrc.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsgSrc.btCmd[3] = CmdId.CMD_DEV_SRC.value
            sendMsgSrc.btCmd[4] = CmdId.GET_SRC_DEV_NO_REQ.value
            sendMsgSrc.btCmd[5] = 0x01
            sendMsgSrc.btCmd[6] = 0x00
            Handler().postDelayed({ (activity as DevUnitMsg).sendBtServiceMsg(sendMsgSrc)}, 2000)

            sendMsgAg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsgAg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsgAg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsgAg.btCmd[3] = 0x20
            sendMsgAg.btCmd[4] = CmdId.SET_AG_CTRL_REQ.value
            sendMsgAg.btCmd[5] = 0x02
            sendMsgAg.btCmd[6] = 0x10
            sendMsgAg.btCmd[7] = (seekTxPowerAg2.progress * 4 - 20).toByte()
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsgAg)
            (activity as DevUnitMsg).getBtDevUnitList()[5].txPowerAg = txPowerAg2
        }

        btnTxPwrRead.setOnClickListener {
            val sendMsgSrc = BtDevMsg(0, 0)
            val sendMsgAg = BtDevMsg(0, 0)

            (activity as DevUnitMsg).setVibrator(200)
            sendMsgSrc.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsgSrc.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsgSrc.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsgSrc.btCmd[3] = CmdId.CMD_DEV_SRC.value
            sendMsgSrc.btCmd[4] = CmdId.GET_HFP_PSKEY_REQ.value
            sendMsgSrc.btCmd[5] = 0x02
            sendMsgSrc.btCmd[6] = 0x00
            sendMsgSrc.btCmd[7] = 0x0a
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsgSrc)

            sendMsgAg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsgAg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsgAg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsgAg.btCmd[3] = CmdId.CMD_DEV_AG_ALL.value
            sendMsgAg.btCmd[4] = CmdId.GET_AG_PSKEY_REQ.value
            sendMsgAg.btCmd[5] = 0x02
            sendMsgAg.btCmd[6] = 0x00
            sendMsgAg.btCmd[7] = 0x0a
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsgAg)
        }
    }

    override fun onPause() {
        super.onPause()

        Logger.d(LogGbl, "FragmentConState on Pause")
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
        when ((activity as DevUnitMsg).getDevType(0)) {
            "M6_SRC" -> {
                seekTxPowerSrc.progress = ((activity as DevUnitMsg).getBtDevUnitList()[0].txPowerHfp + 20) / 4
                txvDistanceSrc.text = String.format("%s(%d)", context?.resources?.getString(R.string.txvDistanceSrc), (activity as DevUnitMsg).getBtDevUnitList()[0].txPowerHfp)
                Logger.d(LogGbl, "txPower SRC ${(activity as DevUnitMsg).getBtDevUnitList()[0].txPowerHfp}")
                seekTxPowerAg0.progress = ((activity as DevUnitMsg).getBtDevUnitList()[1].txPowerAg + 20) / 4
                txvDistanceAg0.text = String.format("%s(%d)", context?.resources?.getString(R.string.txvDistanceAg0), (activity as DevUnitMsg).getBtDevUnitList()[1].txPowerAg)
                Logger.d(LogGbl, "txPower AG0 ${(activity as DevUnitMsg).getBtDevUnitList()[1].txPowerAg}")
                seekTxPowerAg1.progress = ((activity as DevUnitMsg).getBtDevUnitList()[3].txPowerAg + 20) / 4
                txvDistanceAg1.text = String.format("%s(%d)", context?.resources?.getString(R.string.txvDistanceAg1), (activity as DevUnitMsg).getBtDevUnitList()[3].txPowerAg)
                Logger.d(LogGbl, "txPower AG1 ${(activity as DevUnitMsg).getBtDevUnitList()[3].txPowerAg}")
                seekTxPowerAg2.progress = ((activity as DevUnitMsg).getBtDevUnitList()[5].txPowerAg + 20) / 4
                txvDistanceAg2.text = String.format("%s(%d)", context?.resources?.getString(R.string.txvDistanceAg2), (activity as DevUnitMsg).getBtDevUnitList()[5].txPowerAg)
                Logger.d(LogGbl, "txPower AG2 ${(activity as DevUnitMsg).getBtDevUnitList()[5].txPowerAg}")
                if (BtDevUnit.sppStateCon == 0x00.toByte()) {
                    btnTxPwrRead.visibility = View.VISIBLE
                    btnTxPwrWriteSrc.visibility = View.VISIBLE
                    btnTxPwrWriteAg0.visibility = View.VISIBLE
                    btnTxPwrWriteAg1.visibility = View.VISIBLE
                    btnTxPwrWriteAg2.visibility = View.VISIBLE
                    btnTxPwrWriteAll.visibility = View.VISIBLE
                    seekTxPowerSrc.visibility = View.VISIBLE
                    seekTxPowerAg0.visibility = View.VISIBLE
                    seekTxPowerAg1.visibility = View.VISIBLE
                    seekTxPowerAg2.visibility = View.VISIBLE
                }
                else {
                    btnTxPwrRead.visibility = View.INVISIBLE
                    btnTxPwrWriteSrc.visibility = View.INVISIBLE
                    btnTxPwrWriteAg0.visibility = View.INVISIBLE
                    btnTxPwrWriteAg1.visibility = View.INVISIBLE
                    btnTxPwrWriteAg2.visibility = View.INVISIBLE
                    btnTxPwrWriteAll.visibility = View.INVISIBLE
                    seekTxPowerSrc.visibility = View.INVISIBLE
                    seekTxPowerAg0.visibility = View.INVISIBLE
                    seekTxPowerAg1.visibility = View.INVISIBLE
                    seekTxPowerAg2.visibility = View.INVISIBLE
                }
            }
            "DG_BT", "VC_BT" -> {
                seekTxPowerSrc.progress = ((activity as DevUnitMsg).getBtDevUnitList()[0].txPowerHfp + 20) / 4
                txvDistanceSrc.text = String.format("%s(%d)", context?.resources?.getString(R.string.txvDistanceSrc), (activity as DevUnitMsg).getBtDevUnitList()[0].txPowerHfp)
                Logger.d(LogGbl, "txPower SRC ${(activity as DevUnitMsg).getBtDevUnitList()[0].txPowerHfp}")
                if (BtDevUnit.sppStateCon == 0x00.toByte()) {
                    btnTxPwrRead.visibility = View.VISIBLE
                    btnTxPwrWriteSrc.visibility = View.VISIBLE
                    seekTxPowerSrc.visibility = View.VISIBLE
                }
                else {
                    btnTxPwrRead.visibility = View.INVISIBLE
                    btnTxPwrWriteSrc.visibility = View.INVISIBLE
                    seekTxPowerSrc.visibility = View.INVISIBLE
                }
                btnTxPwrWriteAg0.visibility = View.INVISIBLE
                btnTxPwrWriteAg1.visibility = View.INVISIBLE
                btnTxPwrWriteAg2.visibility = View.INVISIBLE
                btnTxPwrWriteAll.visibility = View.INVISIBLE
                seekTxPowerAg0.visibility = View.INVISIBLE
                seekTxPowerAg1.visibility = View.INVISIBLE
                seekTxPowerAg2.visibility = View.INVISIBLE
            }
            else -> {
                btnTxPwrRead?.visibility = View.INVISIBLE
                btnTxPwrWriteSrc?.visibility = View.INVISIBLE
                btnTxPwrWriteAg0?.visibility = View.INVISIBLE
                btnTxPwrWriteAg1?.visibility = View.INVISIBLE
                btnTxPwrWriteAg2?.visibility = View.INVISIBLE
                btnTxPwrWriteAll?.visibility = View.INVISIBLE
                seekTxPowerSrc?.visibility = View.INVISIBLE
                seekTxPowerAg0?.visibility = View.INVISIBLE
                seekTxPowerAg1?.visibility = View.INVISIBLE
                seekTxPowerAg2?.visibility = View.INVISIBLE
            }
        }
    }
}