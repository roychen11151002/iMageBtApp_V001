package com.example.imagebtapp_v001b001

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.view.isInvisible
import kotlinx.android.synthetic.main.fragment_con_state.*
import kotlinx.android.synthetic.main.fragment_feature_set.*
import kotlinx.android.synthetic.main.fragment_vol_set.*
import kotlin.reflect.KVariance

class FragmentVolSet : Fragment() {
    var srcDevItem = 0
    var mode = 8

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.d(LogGbl, "FragmentConState on Create")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vol_set, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        seekAgHfpSpkrVol.visibility = View.INVISIBLE
        seekAgAvSpkrVol.visibility = View.INVISIBLE
        seekAgHfpMicVol.visibility = View.INVISIBLE
        textView6.visibility = View.INVISIBLE
        chkSrcAvSpkrDecade.visibility = View.INVISIBLE
        if((activity as DevUnitMsg).getBtDevUnitList().size == 1) {
            rdHfpAllVolume.isEnabled = false
        }
        else {
            rdHfpAllVolume.isEnabled = true
        }
        btnVolRead.setOnClickListener {
            deviceItemGet()
            for (i in 0 until 2) {
                val sendMsg = BtDevMsg(0, 0)

                sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
                sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
                sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
                sendMsg.btCmd[3] =
                    if(rdGpDevVolume.checkedRadioButtonId == R.id.rdSrcVolume)
                        0x30
                    else
                        0x38
                sendMsg.btCmd[4] = CmdId.GET_HFP_PSKEY_REQ.value
                sendMsg.btCmd[5] = 0x02
                sendMsg.btCmd[6] = 0x00
                sendMsg.btCmd[7] = (17 + i).toByte()
                (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
            }
        }
        btnVolWrite.setOnClickListener {
            val sendMsg = BtDevMsg(0, 0)
            val sendMsgAg = BtDevMsg(0, 0)

            deviceItemGet()
            sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[3] =
                if(rdGpDevVolume.checkedRadioButtonId == R.id.rdSrcVolume)
                    0x30
                else
                    0x38
            sendMsg.btCmd[4] = CmdId.SET_HFP_PSKEY_REQ.value
            sendMsg.btCmd[5] = 0x10
            sendMsg.btCmd[6] = 0x00
            sendMsg.btCmd[7] = 17
            sendMsg.btCmd[8] = (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcWireHfpMicVol.toByte()
            sendMsg.btCmd[9] = (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcWireHfpSpkrVol.toByte()
            sendMsg.btCmd[10] = (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcUsbHfpMicVol.toByte()
            sendMsg.btCmd[11] = (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcUsbHfpSpkrVol.toByte()
            sendMsg.btCmd[12] = (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcBtHfpMicVol.toByte()
            sendMsg.btCmd[13] = (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcBtHfpSpkrVol.toByte()
            sendMsg.btCmd[14] = (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcVcsHfpMicVol.toByte()
            sendMsg.btCmd[15] = (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcVcsHfpSpkrVol.toByte()
            sendMsg.btCmd[16] = (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcWireAvSpkrVol.toByte()
            sendMsg.btCmd[17] = (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcUsbAvSpkrVol.toByte()
            sendMsg.btCmd[18] = (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcBtAvSpkrVol.toByte()
            sendMsg.btCmd[19] = (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcVcsAvSpkrVol.toByte()
            sendMsg.btCmd[20] = (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcSpkrDecade.toByte()
            sendMsg.btCmd[21] = 0x00
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)

            sendMsgAg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsgAg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsgAg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsgAg.btCmd[3] =
                if(rdGpDevVolume.checkedRadioButtonId == R.id.rdSrcVolume)
                    0x30
                else
                    0x38
            sendMsgAg.btCmd[4] = CmdId.SET_HFP_PSKEY_REQ.value
            sendMsgAg.btCmd[5] = 0x10
            sendMsgAg.btCmd[6] = 0x00
            sendMsgAg.btCmd[7] = 18
            sendMsgAg.btCmd[8] = (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeAgWireHfpMicVol.toByte()
            sendMsgAg.btCmd[9] = (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeAgWireHfpSpkrVol.toByte()
            sendMsgAg.btCmd[10] = (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeAgUsbHfpMicVol.toByte()
            sendMsgAg.btCmd[11] = (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeAgUsbHfpSpkrVol.toByte()
            sendMsgAg.btCmd[12] = (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeAgBtHfpMicVol.toByte()
            sendMsgAg.btCmd[13] = (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeAgBtHfpSpkrVol.toByte()
            sendMsgAg.btCmd[14] = (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeAgVcsHfpMicVol.toByte()
            sendMsgAg.btCmd[15] = (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeAgVcsHfpSpkrVol.toByte()
            sendMsgAg.btCmd[16] = (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeAgWireAvSpkrVol.toByte()
            sendMsgAg.btCmd[17] = (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeAgUsbAvSpkrVol.toByte()
            sendMsgAg.btCmd[18] = (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeAgBtAvSpkrVol.toByte()
            sendMsgAg.btCmd[19] = (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeAgVcsAvSpkrVol.toByte()
            sendMsgAg.btCmd[20] = 0x00
            sendMsgAg.btCmd[21] = 0x00
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsgAg)
        }
        rdGpDevVolume.setOnCheckedChangeListener { _, checkedId ->
            deviceItemGet()
            updateData()
        }
        rdGpModeVolume.setOnCheckedChangeListener { _, checkedId ->
            mode = when (checkedId) {
                    R.id.rdModeWireVolume -> 1
                    R.id.rdModeUsbVolume -> 2
                    R.id.rdModeBtVolume -> 4
                    R.id.rdModeVcsVolume -> 8
                    else -> 8
                }
            updateData()
        }
        seekSrcHfpMicVol.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                when (mode) {
                    1 -> if(srcDevItem == 0)
                            (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeAgWireHfpMicVol = seekSrcHfpMicVol.progress
                         else
                            (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcWireHfpMicVol = seekSrcHfpMicVol.progress
                    2 -> (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcUsbHfpMicVol = seekSrcHfpMicVol.progress
                    4 -> (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcBtHfpMicVol = seekSrcHfpMicVol.progress
                    8 -> (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcVcsHfpMicVol = seekSrcHfpMicVol.progress
                    else -> (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcWireHfpMicVol = seekSrcHfpMicVol.progress
                }
            }
        })
        seekSrcHfpSpkrVol.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                when (mode) {
                    1 -> if(srcDevItem == 0)
                            (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeAgWireHfpSpkrVol = seekSrcHfpSpkrVol.progress
                        else
                            (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcWireHfpSpkrVol = seekSrcHfpSpkrVol.progress
                    2 -> (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcUsbHfpSpkrVol = seekSrcHfpSpkrVol.progress
                    4 -> (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcBtHfpSpkrVol = seekSrcHfpSpkrVol.progress
                    8 -> (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcVcsHfpSpkrVol = seekSrcHfpSpkrVol.progress
                    else -> (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcWireHfpSpkrVol = seekSrcHfpSpkrVol.progress
                }
            }
        })
        seekSrcAvSpkrVol.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                when (mode) {
                    1 -> if(srcDevItem == 0)
                            (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeAgWireAvSpkrVol = seekSrcAvSpkrVol.progress
                         else
                            (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcWireAvSpkrVol = seekSrcAvSpkrVol.progress
                    2 -> (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcUsbAvSpkrVol = seekSrcAvSpkrVol.progress
                    4 -> (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcBtAvSpkrVol = seekSrcAvSpkrVol.progress
                    8 -> (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcVcsAvSpkrVol = seekSrcAvSpkrVol.progress
                    else -> (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcWireAvSpkrVol = seekSrcAvSpkrVol.progress
                }
            }
        })
/*
        seekAgHfpMicVol.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                when (mode) {
                    1 -> (activity as DevUnitMsg).getBtDevUnitList()[srcDevItme].modeAgWireHfpMicVol = seekAgHfpMicVol.progress
                    2 -> (activity as DevUnitMsg).getBtDevUnitList()[srcDevItme].modeAgUsbHfpMicVol = seekAgHfpMicVol.progress
                    4 -> (activity as DevUnitMsg).getBtDevUnitList()[srcDevItme].modeAgBtHfpMicVol = seekAgHfpMicVol.progress
                    8 -> (activity as DevUnitMsg).getBtDevUnitList()[srcDevItme].modeAgVcsHfpMicVol = seekAgHfpMicVol.progress
                    else -> (activity as DevUnitMsg).getBtDevUnitList()[srcDevItme].modeAgWireHfpMicVol = seekAgHfpMicVol.progress
                }
            }
        })
        seekAgHfpSpkrVol.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                when (mode) {
                    1 -> (activity as DevUnitMsg).getBtDevUnitList()[srcDevItme].modeAgWireHfpSpkrVol = seekAgHfpSpkrVol.progress
                    2 -> (activity as DevUnitMsg).getBtDevUnitList()[srcDevItme].modeAgUsbHfpSpkrVol = seekAgHfpSpkrVol.progress
                    4 -> (activity as DevUnitMsg).getBtDevUnitList()[srcDevItme].modeAgBtHfpSpkrVol = seekAgHfpSpkrVol.progress
                    8 -> (activity as DevUnitMsg).getBtDevUnitList()[srcDevItme].modeAgVcsHfpSpkrVol = seekAgHfpSpkrVol.progress
                    else -> (activity as DevUnitMsg).getBtDevUnitList()[srcDevItme].modeAgWireHfpSpkrVol = seekAgHfpSpkrVol.progress
                }
            }
        })
        seekAgAvSpkrVol.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                when (mode) {
                    1 -> (activity as DevUnitMsg).getBtDevUnitList()[srcDevItme].modeAgWireAvSpkrVol = seekAgAvSpkrVol.progress
                    2 -> (activity as DevUnitMsg).getBtDevUnitList()[srcDevItme].modeAgUsbAvSpkrVol = seekAgAvSpkrVol.progress
                    4 -> (activity as DevUnitMsg).getBtDevUnitList()[srcDevItme].modeAgBtAvSpkrVol = seekAgAvSpkrVol.progress
                    8 -> (activity as DevUnitMsg).getBtDevUnitList()[srcDevItme].modeAgVcsAvSpkrVol = seekAgAvSpkrVol.progress
                    else -> (activity as DevUnitMsg).getBtDevUnitList()[srcDevItme].modeAgWireAvSpkrVol = seekAgAvSpkrVol.progress
                }
            }
        })
 */
        chkSrcHfpSpkrDecade.setOnCheckedChangeListener { _, isChecked ->
            when (mode) {
                1 -> (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcSpkrDecade =
                    if(isChecked)
                        (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcSpkrDecade.or(0x01)
                    else
                        (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcSpkrDecade.and(0x01.inv())
                2 -> (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcSpkrDecade =
                    if(isChecked)
                        (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcSpkrDecade.or(0x02)
                    else
                        (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcSpkrDecade.and(0x02.inv())
                4 -> (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcSpkrDecade =
                    if(isChecked)
                        (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcSpkrDecade.or(0x04)
                    else
                        (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcSpkrDecade.and(0x04.inv())
                8 -> (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcSpkrDecade =
                    if(isChecked)
                        (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcSpkrDecade.or(0x08)
                    else
                        (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcSpkrDecade.and(0x08.inv())
            }
        }
        chkSrcAvSpkrDecade.setOnCheckedChangeListener { _, isChecked ->
            when (mode) {
                1 -> (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcSpkrDecade =
                    if(isChecked)
                        (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcSpkrDecade.or(0x10)
                    else
                        (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcSpkrDecade.and(0x10.inv())
                2 -> (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcSpkrDecade =
                    if(isChecked)
                        (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcSpkrDecade.or(0x20)
                    else
                        (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcSpkrDecade.and(0x20.inv())
                4 -> (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcSpkrDecade =
                    if(isChecked)
                        (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcSpkrDecade.or(0x40)
                    else
                        (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcSpkrDecade.and(0x40.inv())
                8 -> (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcSpkrDecade =
                    if(isChecked)
                        (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcSpkrDecade.or(0x80)
                    else
                        (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcSpkrDecade.and(0x80.inv())
            }
        }

        mode = when(rdGpModeVolume.checkedRadioButtonId) {
            R.id.rdModeWireVolume -> 1
            R.id.rdModeUsbVolume -> 2
            R.id.rdModeBtVolume -> 4
            R.id.rdModeVcsVolume -> 8
            else -> 8
        }

        deviceItemGet()
        updateData()
    }

    fun deviceItemGet() {
        srcDevItem =
            when(rdGpDevVolume.checkedRadioButtonId) {
                R.id.rdSrcVolume -> 0
                R.id.rdHfpAllVolume -> {
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

    fun updateData() {
        if(btnVolRead != null) {
            when (mode) {
                1 -> {
                    if (srcDevItem == 0) {
                        seekSrcAvSpkrVol.progress =
                            (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeAgWireAvSpkrVol
                        seekSrcHfpSpkrVol.progress =
                            (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeAgWireHfpSpkrVol
                        seekSrcHfpMicVol.progress =
                            (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeAgWireHfpMicVol
                    } else {
                        seekSrcAvSpkrVol.progress =
                            (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcWireAvSpkrVol
                        seekSrcHfpSpkrVol.progress =
                            (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcWireHfpSpkrVol
                        seekSrcHfpMicVol.progress =
                            (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcWireHfpMicVol
                    }
                    seekAgAvSpkrVol.progress =
                        (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeAgWireAvSpkrVol
                    seekAgHfpSpkrVol.progress =
                        (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeAgWireHfpSpkrVol
                    seekAgHfpMicVol.progress =
                        (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeAgWireHfpMicVol
                    chkSrcHfpSpkrDecade.isChecked =
                        if ((activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcSpkrDecade.and(0x01) == 0x01)
                            true
                        else
                            false
                    chkSrcAvSpkrDecade.isChecked =
                        if ((activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcSpkrDecade.and(0x10) == 0x10)
                            true
                        else
                            false
                }
                2 -> {
                    seekSrcAvSpkrVol.progress =
                        (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcUsbAvSpkrVol
                    seekSrcHfpSpkrVol.progress =
                        (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcUsbHfpSpkrVol
                    seekSrcHfpMicVol.progress =
                        (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcUsbHfpMicVol
                    seekAgAvSpkrVol.progress =
                        (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeAgUsbAvSpkrVol
                    seekAgHfpSpkrVol.progress =
                        (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeAgUsbHfpSpkrVol
                    seekAgHfpMicVol.progress =
                        (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeAgUsbHfpMicVol
                    chkSrcHfpSpkrDecade.isChecked =
                        if ((activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcSpkrDecade.and(0x02) == 0x02)
                            true
                        else
                            false
                    chkSrcAvSpkrDecade.isChecked =
                        if ((activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcSpkrDecade.and(0x20) == 0x20)
                            true
                        else
                            false
                }
                4 -> {
                    seekSrcAvSpkrVol.progress =
                        (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcBtAvSpkrVol
                    seekSrcHfpSpkrVol.progress =
                        (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcBtHfpSpkrVol
                    seekSrcHfpMicVol.progress =
                        (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcBtHfpMicVol
                    seekAgAvSpkrVol.progress =
                        (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeAgBtAvSpkrVol
                    seekAgHfpSpkrVol.progress =
                        (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeAgBtHfpSpkrVol
                    seekAgHfpMicVol.progress =
                        (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeAgBtHfpMicVol
                    chkSrcHfpSpkrDecade.isChecked =
                        if ((activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcSpkrDecade.and(0x04) == 0x04)
                            true
                        else
                            false
                    chkSrcAvSpkrDecade.isChecked =
                        if ((activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcSpkrDecade.and(0x40) == 0x40)
                            true
                        else
                            false
                }
                8 -> {
                    seekSrcAvSpkrVol.progress =
                        (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcVcsAvSpkrVol
                    seekSrcHfpSpkrVol.progress =
                        (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcVcsHfpSpkrVol
                    seekSrcHfpMicVol.progress =
                        (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcVcsHfpMicVol
                    seekAgAvSpkrVol.progress =
                        (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeAgVcsAvSpkrVol
                    seekAgHfpSpkrVol.progress =
                        (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeAgVcsHfpSpkrVol
                    seekAgHfpMicVol.progress =
                        (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeAgVcsHfpMicVol
                    chkSrcHfpSpkrDecade.isChecked =
                        if ((activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcSpkrDecade.and(0x08) == 0x08)
                            true
                        else
                            false
                    chkSrcAvSpkrDecade.isChecked =
                        if ((activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcSpkrDecade.and(0x80) == 0x80)
                            true
                        else
                            false
                }
                else -> {
                    seekSrcAvSpkrVol.progress =
                        (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcWireAvSpkrVol
                    seekSrcHfpSpkrVol.progress =
                        (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcWireHfpSpkrVol
                    seekSrcHfpMicVol.progress =
                        (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcWireHfpMicVol
                    seekAgAvSpkrVol.progress =
                        (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeAgWireAvSpkrVol
                    seekAgHfpSpkrVol.progress =
                        (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeAgWireHfpSpkrVol
                    seekAgHfpMicVol.progress =
                        (activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeAgWireHfpMicVol
                    chkSrcHfpSpkrDecade.isChecked =
                        if ((activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcSpkrDecade.and(0x01) == 0x01)
                            true
                        else
                            false
                    chkSrcAvSpkrDecade.isChecked =
                        if ((activity as DevUnitMsg).getBtDevUnitList()[srcDevItem].modeSrcSpkrDecade.and(0x10) == 0x10)
                            true
                        else
                            false
                }
            }
            when (BtDevUnit.sppStateCon) {
                0x00.toByte() -> {
                    btnVolWrite.visibility = View.VISIBLE
                    btnVolRead.visibility = View.VISIBLE
                }
                0x01.toByte() -> {
                    btnVolWrite.visibility = View.INVISIBLE
                    btnVolRead.visibility = View.INVISIBLE
                }
                else -> {
                    btnVolWrite.visibility = View.INVISIBLE
                    btnVolRead.visibility = View.INVISIBLE
                }
            }
        }
    }
}
