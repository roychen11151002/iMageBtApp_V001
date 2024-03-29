package com.example.imagebtapp_v001b001

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_pair_set.*
import java.lang.Integer.parseInt

class FragmentPairSet : Fragment() {
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
        return inflater.inflate(R.layout.fragment_pair_set, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val recyclerListAdapter = BtListAdapter(BtDevUnit.BtList)

        isFragmentReady = true
        recyclerListPair.layoutManager = LinearLayoutManager(context)
        recyclerListPair.adapter = recyclerListAdapter
        recyclerListPair.addItemDecoration(SpaceItemDecoration(4))

        btnPair.setOnClickListener{
            val sendMsg = BtDevMsg(0, 1)

            (activity as DevUnitMsg).setVibrator(200)
            /* (activity as DevUnitMsg).getBtList().removeAll((activity as DevUnitMsg).getBtList())
            (activity as DevUnitMsg).getBtList().add("clear paired device + 00:00:00:00:00:00") */
            recyclerListPair.layoutManager?.scrollToPosition(0)
            sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[3] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[4] = CmdId.SET_INT_PAIR_REQ.value
            sendMsg.btCmd[5] = 0x01
            sendMsg.btCmd[6] = 0x01
            (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
        }

        btnDiscovery.setOnClickListener {
            val sendMsg = BtDevMsg(0, 1)

            (activity as DevUnitMsg).setVibrator(200)
            /* (activity as DevUnitMsg).getBtList().removeAll((activity as DevUnitMsg).getBtList()) */
            when(BtDevUnit.PairState) {
                0x00 -> {
                    recyclerListPair.layoutManager?.scrollToPosition(0)
                    sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
                    sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
                    sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
                    sendMsg.btCmd[3] = CmdId.CMD_DEV_HOST.value
                    sendMsg.btCmd[4] = CmdId.SET_INT_DISCOVERY_REQ.value
                    sendMsg.btCmd[5] = 0x01
                    sendMsg.btCmd[6] = 0x01
                    (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
                }
                0x01 -> {
                    sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
                    sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
                    sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
                    sendMsg.btCmd[3] = CmdId.CMD_DEV_HOST.value
                    sendMsg.btCmd[4] = CmdId.SET_INT_DISCOVERY_REQ.value
                    sendMsg.btCmd[5] = 0x01
                    sendMsg.btCmd[6] = 0x00
                    (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
                }
            }
        }

        btnDiscovery.setOnLongClickListener {
            var sendMsg = BtDevMsg(0, 0)

            sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
            sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
            sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
            sendMsg.btCmd[3] = CmdId.CMD_DEV_SRC.value
            sendMsg.btCmd[4] = CmdId.SET_DISCOVERY_REQ.value
            sendMsg.btCmd[5] = 0x02.toByte()
            sendMsg.btCmd[6] = 0x70.toByte()
            sendMsg.btCmd[7] = 0x20.toByte()

            (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
            true
        }

        recyclerListAdapter.setOnItemClickListener(object : BtListAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val listItem = resources.getStringArray(R.array.txvDevName)
                var sendMsg = BtDevMsg(0, 1)
                var str = BtDevUnit.BtList[position].removeRange(0, BtDevUnit.BtList[position].lastIndexOf(" + ") + 3)
                var strList = str.split(':')
                var preferDataEdit = (activity as DevUnitMsg).getpreferData().edit()

                // setMultiChoiceItems
                AlertDialog.Builder(activity).setTitle(getString(R.string.txvDevPair)).setItems(listItem) {
                    _, which ->
                    (activity as DevUnitMsg).setVibrator(200)
                    sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
                    sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
                    sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
                    sendMsg.btCmd[5] = 0x07
                    if(which == 0) {
                        preferDataEdit.putString("bdaddr0", str)
                        preferDataEdit.apply()

                        sendMsg.btCmd[3] = CmdId.CMD_DEV_HOST.value
                        sendMsg.btCmd[4] = CmdId.SET_INT_CON_REQ.value
                        sendMsg.btCmd[6] = 0x01
                    }
                    else {
                        sendMsg.btCmd[3] = getDevId(which)
                        sendMsg.btCmd[4] = CmdId.SET_HFP_PAIR_REQ.value
                        sendMsg.btCmd[6] = 0x00
                    }
                    sendMsg.btCmd[7] = parseInt(strList[3], 16).toByte()
                    sendMsg.btCmd[8] = parseInt(strList[4], 16).toByte()
                    sendMsg.btCmd[9] = parseInt(strList[5], 16).toByte()
                    sendMsg.btCmd[10] = parseInt(strList[2], 16).toByte()
                    sendMsg.btCmd[11] = parseInt(strList[0], 16).toByte()
                    sendMsg.btCmd[12] = parseInt(strList[1], 16).toByte()
                    Logger.d(LogGbl, "${String.format("bdaddr %02X %02X %02X %02X %02X %02X ", sendMsg.btCmd[11], sendMsg.btCmd[12], sendMsg.btCmd[10], sendMsg.btCmd[7], sendMsg.btCmd[8], sendMsg.btCmd[9])}")
                    Handler().postDelayed({(activity as DevUnitMsg).sendBtServiceMsg(sendMsg)}, 100)
                }.setNegativeButton("Cancel") {
                    _, _ ->
                }.show()
            }
        })
/*
        recyclerListAdapter.setOnItemLongClickListener(object : BtListAdapter.OnItemLongClickListener {
            override fun onItemLongClick(view: View, position: Int): Boolean {
                var sendMsg = BtDevMsg(1, 1)
                var str = (activity as DevUnitMsg).getBtList()[position].removeRange(0, (activity as DevUnitMsg).getBtList()[position].lastIndexOf(" + ") + 3)
                var strList = str.split(':')
                var preferDataEdit = (activity as DevUnitMsg).getpreferData().edit()

                preferDataEdit.putString("bdaddr1", str)
                preferDataEdit.apply()
                sendMsg.btCmd[0] = CmdId.CMD_HEAD_FF.value
                sendMsg.btCmd[1] = CmdId.CMD_HEAD_55.value
                sendMsg.btCmd[2] = CmdId.CMD_DEV_HOST.value
                sendMsg.btCmd[3] = CmdId.CMD_DEV_HOST.value
                sendMsg.btCmd[4] = CmdId.SET_INT_CON_REQ.value
                sendMsg.btCmd[5] = 0x07
                sendMsg.btCmd[6] = 0x01
                sendMsg.btCmd[7] = parseInt(strList[3], 16).toByte()
                sendMsg.btCmd[8] = parseInt(strList[4], 16).toByte()
                sendMsg.btCmd[9] = parseInt(strList[5], 16).toByte()
                sendMsg.btCmd[10] = parseInt(strList[2], 16).toByte()
                sendMsg.btCmd[11] = parseInt(strList[0], 16).toByte()
                sendMsg.btCmd[12] = parseInt(strList[1], 16).toByte()
                Logger.d(LogGbl, "${String.format("bdaddr %02X %02X %02X %02X %02X %02X ", sendMsg.btCmd[11], sendMsg.btCmd[12], sendMsg.btCmd[10], sendMsg.btCmd[7], sendMsg.btCmd[8], sendMsg.btCmd[9])}")
                (activity as DevUnitMsg).sendBtServiceMsg(sendMsg)
                return true
            }
        })
 */
        updateData()
    }

    override fun onPause() {
        super.onPause()

        Logger.d(LogGbl, "FragmentConState on Pause")
        isFragmentReady = false
    }

    fun getDevId(dev: Int): Byte =
        when(dev) {
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
/*
        if(isFragmentReady == false) {
            Handler().postDelayed({updateData()}, 100)
            Logger.d(LogGbl, "fragment not ready")
            return
        }
*/
        recyclerListPair.adapter?.notifyDataSetChanged()
        txvPairTitle.text =
            when(BtDevUnit.PairState) {
                0 -> {
                    btnPair.isEnabled = true
                    btnDiscovery.isEnabled = true
                    context?.resources?.getString(R.string.txvStaDiscoveryEnd)
                }
                1 -> {
                    btnPair.isEnabled = false
                    context?.resources?.getString(R.string.txvStaDiscovey)
                }
                2 -> {
                    btnPair.isEnabled = false
                    context?.resources?.getString(R.string.txvStaDiscoveryStart)
                }
                3 -> {
                    btnPair.isEnabled = false
                    btnDiscovery.isEnabled = false
                    context?.resources?.getString(R.string.txvStaPaired)
                }
                else -> {
                    btnPair.isEnabled = true
                    btnDiscovery.isEnabled = true
                    context?.resources?.getString(R.string.txvPairTitle)
                }
        }
    }
}
