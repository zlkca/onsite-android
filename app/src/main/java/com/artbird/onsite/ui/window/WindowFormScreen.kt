package com.artbird.onsite.ui.window

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.artbird.onsite.domain.*
import com.artbird.onsite.ui.building.BuildingViewModel
import com.artbird.onsite.ui.components.*
import com.artbird.onsite.ui.utils.convertToInt
import com.artbird.onsite.ui.utils.intToString
import java.util.*

data class UILockerSize(
    var left: String,
    var right: String,
    var top: String,
    var bottom: String,
)
//data class UILockerPosition(
//    var vertical: String,
//    var horizontal: String,
//)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun WindowFormScreen(
    navController: NavController,
    windowViewModel: WindowViewModel,
    windowId: String,
//    buildingViewModel: BuildingViewModel,
//    appointment: Appointment,
//    buildingId: String,
//    floorId: String,
    roomId: String,
//    windowId: String?,
) {
    val windowState by windowViewModel.window.observeAsState()

    var window: Window by remember { mutableStateOf(Window()) }

    LaunchedEffect(key1 = windowId){
        if(windowId != null && windowId != "new") {
            windowViewModel.getWindow(windowId)
        }
    }

    LaunchedEffect(key1 = windowState ){
        if(windowState != null) {
            window = windowState!!
        }
    }



    WindowForm(
        window = window,
        onChange = {name, value ->
            when(name){
                "height-left" -> window = window.copy(height=window.height.copy(left=value))
                "height-middle" -> window = window.copy(height=window.height.copy(mid=value))
                "height-right" -> window = window.copy(height=window.height.copy(right=value))
                "width-top" -> window = window.copy(width=window.width.copy(top=value))
                "width-middle" -> window = window.copy(width=window.width.copy(mid=value))
                "width-bottom" -> window = window.copy(width=window.width.copy(bottom=value))
                "numOfWindows" -> window = window.copy(numOfWindows = value)
                }
            },
        onSave = {
            if (window!= null && windowId != "new") {
                //  windowViewModel.updateWindow(windowId!!, data)
            } else {
                windowViewModel.createWindow(it.copy(roomId=roomId))
            }
            navController.popBackStack()
        }
    )
}

//    val windowState by windowViewModel.window.observeAsState()
//    val building by buildingViewModel.building.observeAsState()
//    var room by remember { mutableStateOf(Room("","","")) }
//
//    val scrollState = rememberScrollState()
//
//    var window by remember { mutableStateOf(Window()) }
//
//    var name by remember { mutableStateOf("") }
//    var notes by remember { mutableStateOf("") }
//
//    var width by remember { mutableStateOf(UIWindowWidth()) }
//    var height by remember { mutableStateOf(UIWindowHeight()) }
//
//    var numOfWindows by remember { mutableStateOf("") }
//    var type by remember { mutableStateOf("Normal") }
//    var position by remember { mutableStateOf("L") }
//    var openingDirection by remember { mutableStateOf("L") }
//    var mountPosition by remember { mutableStateOf("Inside") }
//
//    var bladeSize by remember { mutableStateOf("2.5 Inches") }
//    var leverType by remember { mutableStateOf("Invisible") }
//    var bafflePosition by remember { mutableStateOf(UIBafflePosition()) }
//    var frameStyle by remember { mutableStateOf("L Frame") }
//    var originalFrameStyle by remember { mutableStateOf("Normal") }
//
//    // There are 2 lockers by default
//    var lockers: List<Locker> by remember { mutableStateOf(arrayListOf(
//        Locker(id = UUID.randomUUID().toString()),
//        Locker(id = UUID.randomUUID().toString()),
//    )) }
//
//    fun clickBladeOption(it: OptionItem){
//        bladeSize = it.label
//    }
//
//    fun clickLeverTypeOption(it: OptionItem){
//        leverType = it.label
//    }
//
//    fun clickTypeOption(it: OptionItem){
//        type = it.label
//    }
//
//    fun clickInstallOption(it: OptionItem){
//        mountPosition = it.label
//    }
//
//    fun clickFrameStyleOption(it: OptionItem){
//        frameStyle = it.label
//    }
//
//    fun clickOriginFrameStyleOption(it: OptionItem){
//        originalFrameStyle = it.label
//    }
//
//    val bladeOptions = listOf(
//        OptionItem("2.5 Inches",::clickBladeOption),
//        OptionItem("3.5 Inches",::clickBladeOption),
//    )
//    val leverOptions = listOf(
//        OptionItem("Invisible",::clickLeverTypeOption),
//        OptionItem("Others",::clickLeverTypeOption),
//    )
//    val mountPositionOptions = listOf(
//        OptionItem("Inside", ::clickInstallOption),
//        OptionItem("Outside", ::clickInstallOption),
//    )
//    val typeOptions = listOf(
//        OptionItem("Normal",::clickTypeOption),
//        OptionItem("Bay",::clickTypeOption),
//        OptionItem("Arch",::clickTypeOption),
//        OptionItem("Slide",::clickTypeOption),
//        OptionItem("Bi-Fold",::clickTypeOption),
//        OptionItem("High",::clickTypeOption),
//    )
//    val frameStyleOptions = listOf(
//        OptionItem("L Frame", ::clickFrameStyleOption),
//        OptionItem("Z Frame", ::clickFrameStyleOption),
//        OptionItem("Regal Z Frame", ::clickFrameStyleOption),
//        OptionItem("Casing", ::clickFrameStyleOption),
//    )
//    val originalFrameStyleOptions = listOf(
//        OptionItem("Normal", ::clickOriginFrameStyleOption),
//        OptionItem("Protruding Window Sill", ::clickOriginFrameStyleOption),
//        OptionItem("Obstructed Corner", ::clickOriginFrameStyleOption),
//    )
//
//    LaunchedEffect(key1 = buildingId) {
//        if (buildingId != null && buildingId != "new") {
//            buildingViewModel.getBuilding(buildingId)
//        }
//    }
//
//    LaunchedEffect(key1 = building) {
//        if (building != null) {
//            val floor = building!!.floors.find { it -> it._id == floorId}
//            room = floor!!.rooms.find { it -> it._id == roomId}!!
//        }
//    }
//
//    LaunchedEffect(key1 = windowId){
//        if(windowId != null && windowId != "new") {
//            windowViewModel.getWindow(windowId)
//        }
//    }
//
//    LaunchedEffect(key1 = window ){
//        if(window != null && windowId != "new") {
//            name = window?.name.toString()
//            notes = window?.notes.toString()
//            width = UIWindowWidth(
////                top =toImparialLength(window?.width!!.top),
////                mid =toImparialLength(window?.width!!.mid),
////                bottom =toImparialLength(window?.width!!.bottom),
//            )
//            height = UIWindowHeight(
////                left =toImparialLength(window?.height!!.left),
////                mid =toImparialLength(window?.height!!.mid),
////                right =toImparialLength(window?.height!!.right),
//            )
//            numOfWindows = intToString(window?.numOfWindows!!)
//            type = window?.type !!
//            position = window?.position!!
//            openingDirection = window?.openingDirection !!
//            mountPosition = window?.mountPosition!!
//            bladeSize = window?.bladeSize !!
//            leverType = window?.leverType !!
//            bafflePosition = UIBafflePosition(
////                toImparialLength(window?.bafflePosition!!.top),
////                toImparialLength(window?.bafflePosition!!.bottom),
//            )
//            originalFrameStyle = window?.originalFrameStyle!!
//            frameStyle = window?.frameStyle!!
//
//            if(window?.lockers!!.size > 0){
//                lockers = window?.lockers!!
//            }
//        }
//    }
//
//    fun getValidLockers(): ArrayList<Locker> {
//        val locks = ArrayList<Locker>();
//        lockers.forEach {
////            if (it.size.top != 0 && it.size.left != 0 && it.size.bottom != 0 && it.size.right != 0 ) {
////                locks.add(it)
////            }
//        }
//        return locks;
//    }
//
//    fun handleSubmit(){
//        val data = Window(
//            name = name,
//            notes = notes,
////            height = WindowHeight(
////                toInchFragments(height.left),
////                toInchFragments(height.mid),
////                toInchFragments(height.right),
////            ),
////            width = WindowWidth(
////                toInchFragments(width.top),
////                toInchFragments(width.mid),
////                toInchFragments(width.bottom)
////            ),
////            type = type,
////            position = position,
////            openingDirection = openingDirection,
////            numOfWindows = convertToInt(numOfWindows),
////            mountPosition = mountPosition,
////            bafflePosition = BafflePosition(
////                toInchFragments(bafflePosition.top),
////                toInchFragments(bafflePosition.bottom)
////            ),
//            bladeSize = bladeSize,
//            leverType = leverType,
//            frameStyle = frameStyle,
//            lockers = getValidLockers(),
////            room = BaseEntity(roomId, room!!.name), // empty
////            appointment = BaseAppointment(appointment!!._id, appointment!!.title) // empty
//        )
//
//        Log.d("zlk", "submit window form: windowId - ${windowId}")
//        if (window!= null && windowId != "new") {
//            windowViewModel.updateWindow(windowId!!, data)
//        } else {
//            windowViewModel.createWindow(data)
//        }
//
//        navController.popBackStack()
//    }
//
//    fun updateLockerPosition(lockerId: String, name: String, pos: String){
//        val locks = ArrayList<Locker>();
//        lockers.forEach {
//            if (it.id == lockerId) {
//                if (name == "vertical") {
//                    locks.add(
//                        Locker(
//                            id = it.id,
//                            size = it.size,
//                            position = LockerPosition(pos, it.position.horizontal)
//                        )
//                    )
//                } else {
//                    locks.add(
//                        Locker(
//                            id = it.id,
//                            size = it.size,
//                            position = LockerPosition(it.position.vertical, pos)
//                        )
//                    )
//                }
//            }else{
//                locks.add(it)
//            }
//        }
//        lockers = locks;
//    }
//    fun updateLockerSize(lockerId: String, name: String, len: ImparialLength){
//        val locks = ArrayList<Locker>();
//        lockers.forEach {
//            if(it.id == lockerId){
//                when (name) {
//                    "left" -> {
//                        locks.add(
//                            Locker(
//                                id = it.id,
//                                it.position,
////                                LockerSize(it.size.top, it.size.bottom, toInchFragments(len), it.size.right)
//                            )
//                        )
//                    }
//                    "right" -> {
//                        locks.add(
//                            Locker(
//                                id = it.id,
//                                it.position,
////                                LockerSize(it.size.top, it.size.bottom, it.size.left, toInchFragments(len))
//                            )
//                        )
//                    }
//                    "top" -> {
//                        locks.add(
//                            Locker(
//                                id = it.id,
//                                it.position,
////                                LockerSize(toInchFragments(len), it.size.bottom, it.size.left, it.size.right)
//                            )
//                        )
//                    }
//                    "bottom" -> {
//                        locks.add(
//                            Locker(
//                                id = it.id,
//                                it.position,
////                                LockerSize(it.size.top, toInchFragments(len), it.size.left, it.size.right)
//                            )
//                        )
//                    }
//                }
//            }else{
//                locks.add(it)
//            }
//        }
//
//        lockers =  locks;
//    }
//
//    WindowForm(
//        window,
//        onCancel = {
//            if(windowId != "new"){
//                Log.d("zlk", "Window form for edit: buildings/${buildingId}/floors/${floorId}/rooms/${roomId}/windows/${windowId}/form")
//                navController.navigate("buildings/${buildingId}/floors/${floorId}/rooms/${roomId}/windows/${windowId}/form")
//            }else{
//                Log.d("zlk", "Window form for new: buildings/${buildingId}/floors/${floorId}/rooms/${roomId}/windows/new/form")
//                navController.navigate("buildings/${buildingId}/floors/${floorId}/rooms/${roomId}/windows/new/form")
//            }
//        },
//        onSave = ::handleSubmit
//    )
//}

