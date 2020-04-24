package com.example.imagebtapp_v001b001

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_audio_para_set.*

class FragmentAudioParaSet : Fragment() {
    var srcDevId = 0x38.toByte()
    var srcModeId = 88.toByte()
    var srcparaId = 0

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
        rdGpDevAudioPara.setOnCheckedChangeListener { group, checkedId ->
            srcDevId = when(checkedId) {
                R.id.rdA6AudioPara -> 0x38
                R.id.rdA7AudioPara -> 0x38
                R.id.rdAllAudioPara -> 0x38
                else -> 0x38
            }
        }
        rdGpModeAudioPara.setOnCheckedChangeListener { group, checkedId ->
            srcModeId = when(checkedId) {
                R.id.rdModeUsbAudioPara -> 94
                R.id.rdModeBtAudioPara -> 84
                R.id.rdModeVcsAudioPara -> 86
                R.id.rdModeWireAudioPara -> 88
                else -> 86
            }
            Logger.d(LogGbl, "parameter mode $srcModeId")
        }
        rdGpParaAudioPara.setOnCheckedChangeListener { group, checkedId ->
            srcparaId = when(checkedId) {
                R.id.rdAudioPara0 -> 0
                R.id.rdAudioPara1 -> 1
                R.id.rdAudioPara2 -> 2
                R.id.rdAudioPara3 -> 3
                else -> 0
            }
        }
        btnAudioParaWrite.setOnClickListener {
            val sendMsg = BtDevMsg(0, 0)

            sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[3] = srcDevId
            sendMsg.btCmd[4] = CmdId.SET_HFP_PSKEY_REQ.value
            sendMsg.btCmd[5] = 0x13 // length??
            sendMsg.btCmd[6] = 0x00
            sendMsg.btCmd[7] = srcModeId
            // (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
            Logger.d(LogGbl, "audio parameter send")
        }
    }
}
