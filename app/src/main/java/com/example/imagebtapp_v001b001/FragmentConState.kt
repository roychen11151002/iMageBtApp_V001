package com.example.imagebtapp_v001b001

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_con_state.*
import kotlin.experimental.and
import kotlin.experimental.or

class FragmentConState : Fragment() {
    private val DevIconImage = 101
    private var positionEdit = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.d(LogGbl, "FragmentConState on Create")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Logger.d(LogGbl, "FragmentConState on Create View")
        return inflater.inflate(R.layout.fragment_con_state, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var strIndMsg = resources.getStringArray(R.array.txvStaName)
        val devUnitAdapter = BtDevUnitAdapter((activity as DevUnitMsg).getBtDevUnitList(), strIndMsg)
        val lmg = GridLayoutManager(context, 2)

        Logger.d(LogGbl, "FragmentConState on Activity created")
        lmg.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                if(position == 0)
                    return 2
                else
                    return 1
            }
        }
        recyclerDevList.layoutManager = lmg
        recyclerDevList.adapter = devUnitAdapter
        recyclerDevList.addItemDecoration(SpaceItemDecoration(4))
        //strIndMsg[0] = context!!.resources.getString(R.string.txvStaRssiStrong)
        //strIndMsg[1] = context!!.resources.getString(R.string.txvStaRssiWeak)
        //strIndMsg[2] = context!!.resources.getString(R.string.txvStaBat)
        //strIndMsg[3] = context!!.resources.getString(R.string.txvStaBatLow)
        //strIndMsg[4] = context!!.resources.getString(R.string.txvStaBatChg)
        //strIndMsg[5] = context!!.resources.getString(R.string.txvStaBatPwr)

        devUnitAdapter.setOnkItemImageListener(object : BtDevUnitAdapter.OnItemImageListener {
            override fun onItemImage(position: Int, btDevUnit: BtDevUnit) {
                val txvMsgName = resources.getStringArray(R.array.txvStaName)
                val msgItem = arrayOf(String.format("${resources.getStringArray(R.array.txvMessageName)[0]} ${btDevUnit.nameAlias}"),
                                                    String.format("${resources.getStringArray(R.array.txvMessageName)[1]} ${btDevUnit.nameLocalHfp}"),
                                                    String.format("${resources.getStringArray(R.array.txvMessageName)[2]} ${btDevUnit.nameLocalAg}"),
                                                    String.format("${resources.getStringArray(R.array.txvMessageName)[3]} ${btDevUnit.verFirmwareHfp}"),
                                                    String.format("${resources.getStringArray(R.array.txvMessageName)[4]} ${btDevUnit.verFirmwareAg}"),
                                                    String.format("${resources.getStringArray(R.array.txvMessageName)[5]} ${btDevUnit.bdaddr}"),
                                                    String.format("${resources.getStringArray(R.array.txvMessageName)[6]} ${btDevUnit.bdaddrPair}"))

                AlertDialog.Builder(activity).setTitle(getString(R.string.txvDevMsg)).setItems(msgItem) { dialog, which ->
                }.setPositiveButton("OK") { dialog, which ->
                }.show()
                Logger.d(LogGbl, "btdevunit on item click")
                // Toast.makeText(activity, "${btDevUnit.verFirmwareAg}\n${btDevUnit.localNameHfp}", Toast.LENGTH_LONG).show()
             }
        })
        devUnitAdapter.setOnLongItemImageListener(object : BtDevUnitAdapter.OnLongItemImageLisener {
            override fun onLongItemImage(position: Int, btDevUnit: BtDevUnit) {
                var intent = Intent(Intent.ACTION_OPEN_DOCUMENT)

                intent.setType("image/*")
                startActivityForResult(intent, DevIconImage)
                positionEdit = position
            }
        })
        devUnitAdapter.setOnLongNameEditListener(object : BtDevUnitAdapter.OnLongNameListener {
            override fun onLongNameEdit(position: Int, btDevUnit: BtDevUnit) {
                val intent = Intent(context, MsgEditActivity::class.java)
                val bundle = Bundle()

                positionEdit = position
                bundle.putInt("position", position)
                bundle.putString("nameAlias", btDevUnit.nameAlias)
                intent.putExtras(bundle)
                startActivityForResult(intent, 100)
            }
        })
        devUnitAdapter.setOnSpkrVolListener(object : BtDevUnitAdapter.OnSpkrVolListener {
            override fun onSpkrVol(position: Int, progress: Int, fromUser: Boolean) {
                if(fromUser) {
                    val sendMsg = BtDevMsg(0, 0)

                    sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
                    sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
                    sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
                    sendMsg.btCmd[3] = getDevId(position)
                    sendMsg.btCmd[4] = CmdId.SET_HFP_VOL_REQ.value
                    sendMsg.btCmd[5] = 0x01
                    sendMsg.btCmd[6] = progress.toByte().or(0x20.toByte())
                    (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
                }
                Logger.d(LogGbl, "spkr volume id:$position progress:$progress")
            }
        })
        devUnitAdapter.setOnSpkrMuteListener(object : BtDevUnitAdapter.OnSpkrMuteListener {
            override fun onSpkrMute(position: Int, mute: Boolean) {
                val sendMsg = BtDevMsg(0, 0)

                sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
                sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
                sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
                sendMsg.btCmd[3] = getDevId(position)
                sendMsg.btCmd[4] = CmdId.SET_HFP_VOL_REQ.value
                sendMsg.btCmd[5] = 0x01
                sendMsg.btCmd[6] =
                    if(mute)
                        (activity as DevUnitMsg).getBtDevUnitList()[position].volSpkrHfp.toByte().and(0x0f.toByte())or(0x00.toByte()).or(0x20.toByte())
                    else
                        (activity as DevUnitMsg).getBtDevUnitList()[position].volSpkrHfp.toByte().and(0x0f.toByte())or(0x80.toByte()).or(0x20.toByte())
                (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
                Logger.d(LogGbl, "spkr mute id:$position $mute")
            }
        })
        devUnitAdapter.setOnLongSpkrMuteListener(object : BtDevUnitAdapter.OnLongSpkrMuteListener {
            override fun onLongSpkrMute(position: Int, mute: Boolean) {
                val sendMsg = BtDevMsg(0, 0)

                if (position == 0) {
                    sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
                    sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
                    sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
                    sendMsg.btCmd[3] = getDevId(position)
                    sendMsg.btCmd[4] = CmdId.SET_HFP_VOL_REQ.value
                    sendMsg.btCmd[5] = 0x01
                    sendMsg.btCmd[6] =
                        if(mute)
                            (activity as DevUnitMsg).getBtDevUnitList()[position].volSpkrHfp.toByte().and(0x0f.toByte())or(0x00.toByte()).or(0x20.toByte())
                        else
                            (activity as DevUnitMsg).getBtDevUnitList()[position].volSpkrHfp.toByte().and(0x0f.toByte())or(0x80.toByte()).or(0x20.toByte())
                    (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
                    Logger.d(LogGbl, "spkr mute id:$position $mute")
                } else {
                    sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
                    sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
                    sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
                    sendMsg.btCmd[3] = CmdId.CMD_DEV_HFP_ALL.value
                    sendMsg.btCmd[4] = CmdId.SET_HFP_VOL_REQ.value
                    sendMsg.btCmd[5] = 0x01
                    sendMsg.btCmd[6] =
                        if (mute)
                            (activity as DevUnitMsg).getBtDevUnitList()[position].volSpkrHfp.toByte().and(0x0f.toByte()) or (0x00.toByte()).or(0x20.toByte())
                        else
                            (activity as DevUnitMsg).getBtDevUnitList()[position].volSpkrHfp.toByte().and(0x0f.toByte()) or (0x80.toByte()).or(0x20.toByte())
                    (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
                    Logger.d(LogGbl, "spkr mute id:$position $mute")
                }
            }
        })
        devUnitAdapter.setOnMicMuteListener(object : BtDevUnitAdapter.OnMicMuteListener{
            override fun onMicMute(position: Int, mute: Boolean) {
                val sendMsg = BtDevMsg(0, 0)

                sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
                sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
                sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
                sendMsg.btCmd[3] = getDevId(position)
                sendMsg.btCmd[4] = CmdId.SET_HFP_VOL_REQ.value
                sendMsg.btCmd[5] = 0x01
                if(mute)
                    sendMsg.btCmd[6] = 0x00.toByte().or(0x10.toByte())
                else
                    sendMsg.btCmd[6] =0x40.toByte().or(0x10.toByte())
                (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
                Logger.d(LogGbl, "mic mute id:$position $mute")
            }
        })
        devUnitAdapter.setOnLongMicMuteListener(object : BtDevUnitAdapter.OnLongMicMuteListener {
            override fun onLongMicMute(position: Int, mute: Boolean) {
                val sendMsg = BtDevMsg(0, 0)

                if (position == 0) {
                    sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
                    sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
                    sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
                    sendMsg.btCmd[3] = getDevId(position)
                    sendMsg.btCmd[4] = CmdId.SET_HFP_VOL_REQ.value
                    sendMsg.btCmd[5] = 0x01
                    sendMsg.btCmd[6] =
                        if(mute)
                            0x00.toByte().or(0x10.toByte())
                        else
                            0x40.toByte().or(0x10.toByte())
                    (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
                    Logger.d(LogGbl, "mic mute id:$position $mute")

                } else {
                    sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
                    sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
                    sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
                    sendMsg.btCmd[3] = CmdId.CMD_DEV_AG_ALL.value
                    sendMsg.btCmd[4] = CmdId.SET_HFP_VOL_REQ.value
                    sendMsg.btCmd[5] = 0x01
                    sendMsg.btCmd[6] =
                        if (mute)
                            0x00.toByte().or(0x10.toByte())
                        else
                            0x40.toByte().or(0x10.toByte())
                    (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
                    Logger.d(LogGbl, "mic mute id:$position $mute")
                }
            }
        })
        devUnitAdapter.setOnTalkListener(object : BtDevUnitAdapter.OnTalkListener {
            override fun onTalk(position: Int) {
                val sendMsg = BtDevMsg(0, 0)

                if((activity as DevUnitMsg).getBtDevUnitList()[position].stateCon.and(0x00000220) == 0x00000220) {
                    sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
                    sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
                    sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
                    sendMsg.btCmd[3] = CmdId.CMD_DEV_SRC.value
                    sendMsg.btCmd[4] = CmdId.SET_HFP_EXT_STA_REQ.value
                    sendMsg.btCmd[5] = 0x02
                    sendMsg.btCmd[6] = 0x00
                    sendMsg.btCmd[7] = 0x81.toByte()
                    (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
                    Logger.d(LogGbl, "talk click id:$position")
                }
                else {
                    val strList = (activity as DevUnitMsg).getBtDevUnitList()[position].bdaddrPair.split(':')

                    sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
                    sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
                    sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
                    sendMsg.btCmd[3] = getDevId(position)
                    sendMsg.btCmd[4] = CmdId.SET_HFP_PAIR_REQ.value
                    sendMsg.btCmd[5] = 0x07
                    sendMsg.btCmd[6] = 0x00
                    sendMsg.btCmd[7] = Integer.parseInt(strList[3], 16).toByte()
                    sendMsg.btCmd[8] = Integer.parseInt(strList[4], 16).toByte()
                    sendMsg.btCmd[9] = Integer.parseInt(strList[5], 16).toByte()
                    sendMsg.btCmd[10] = Integer.parseInt(strList[2], 16).toByte()
                    sendMsg.btCmd[11] = Integer.parseInt(strList[0], 16).toByte()
                    sendMsg.btCmd[12] = Integer.parseInt(strList[1], 16).toByte()
                    Logger.d(LogGbl, "${String.format("bdaddr %02X %02X %02X %02X %02X %02X ", sendMsg.btCmd[11], sendMsg.btCmd[12], sendMsg.btCmd[10], sendMsg.btCmd[7], sendMsg.btCmd[8], sendMsg.btCmd[9])}")
                    (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
                }
            }
        })
        devUnitAdapter.setOnLongTalkListener(object : BtDevUnitAdapter.OnLongTalkListener {
            override fun onLongTalk(position: Int) {
                val sendMsg = BtDevMsg(0, 0)

                sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
                sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
                sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
                sendMsg.btCmd[3] = CmdId.CMD_DEV_SRC.value
                sendMsg.btCmd[4] = CmdId.SET_HFP_EXT_STA_REQ.value
                sendMsg.btCmd[5] = 0x02
                sendMsg.btCmd[6] = 0x00
                sendMsg.btCmd[7] = 0x80.toByte()
                (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
                Logger.d(LogGbl, "talk long click id:$position")
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            100 -> {
                when(resultCode) {
                    Activity.RESULT_OK -> {
                        var preferDataEdit = (activity as DevUnitMsg).getpreferData().edit()
                        val sendMsg = BtDevMsg(0, 0)
                        val nameAlias = data?.getStringExtra("nameAlias") ?: "hfp alias name"
                        var s: Int

                        (activity as DevUnitMsg).getBtDevUnitList()[positionEdit].nameAlias = nameAlias
                        preferDataEdit.putString("nameAlias$positionEdit", nameAlias)
                        preferDataEdit.apply()
                        updateData()
                        sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
                        sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
                        sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
                        sendMsg.btCmd[3] = getDevId(positionEdit)
                        sendMsg.btCmd[4] = CmdId.SET_HFP_PSKEY_REQ.value
                        sendMsg.btCmd[5] = (nameAlias.length * 2 + 2).toByte()
                        sendMsg.btCmd[6] = 0x00
                        sendMsg.btCmd[7] = 26
                        for (i in 0 until nameAlias.length) {
                            s = nameAlias[i].toInt()
                            sendMsg.btCmd[i * 2 + 8] = s.shr(8).toByte()
                            sendMsg.btCmd[i * 2 + 8 + 1] = s.and(0x00ff).toByte()
                        }
                        (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
                        Logger.d(LogGbl, "OK result code")
                    }
                    Activity.RESULT_CANCELED -> {
                        Logger.d(LogGbl, "CANCEL result code")
                    }
                    else -> {
                        Logger.d(LogGbl, "other result code")
                    }
                }
            }
            DevIconImage -> {
                var preferDataEdit = (activity as DevUnitMsg).getpreferData().edit()

                if(resultCode == Activity.RESULT_OK) {
                    preferDataEdit.putString("imgIconUri$positionEdit", data?.data.toString())
                    preferDataEdit.apply()
                    (activity as DevUnitMsg).getBtDevUnitList()[positionEdit].imgIconFlash = true
                    (activity as DevUnitMsg).getBtDevUnitList()[positionEdit].imgIconUri = data?.data
                    updateData()
                    Logger.d(LogGbl, "result code OK; image icon URI: ${data?.data?.toString()}")
                }
                else
                    Logger.d(LogGbl, "result code $resultCode")
            }
            else -> {
                Logger.d(LogGbl, "other request code")
            }
        }
    }

    fun getDevId(position: Int): Byte =
        when(position) {
            0 -> 0x30
            1 -> 0x00
            2 -> 0x08
            3 -> 0x10
            4 -> 0x18
            5 -> 0x20
            6 -> 0x28
            else -> 0xff.toByte()
        }

    fun updateData() {
        recyclerDevList.adapter!!.notifyDataSetChanged()
        recyclerDevList.visibility =
            when(BtDevUnit.sppStateCon) {
                0x00.toByte() -> {
                     VISIBLE
                }
                0x01.toByte() -> {
                    INVISIBLE
               }
                else -> {
                    INVISIBLE
                }
            }
    }
}
