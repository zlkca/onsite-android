package com.artbird.onsite.ui.building

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.artbird.onsite.domain.*
import com.artbird.onsite.ui.client.AccountListItem
import com.artbird.onsite.ui.client.ClientForm
import com.artbird.onsite.ui.components.ActionChip
import com.artbird.onsite.ui.components.Body3
import com.artbird.onsite.ui.components.ListActionBar
import com.artbird.onsite.ui.components.Title2
import com.artbird.onsite.ui.theme.SLTheme

@Composable
fun BuildingListItem(item: Building, selected: Boolean, index:Int){
    val colorScheme = MaterialTheme.colorScheme
    Column(){
        Title2(
            text = item.name,
            color = if (selected) colorScheme.onPrimary else colorScheme.onBackground
        )

        Body3(
            text = item.notes,
            color = if (selected) colorScheme.onPrimary else colorScheme.onBackground,
        )
    }
}

@Composable
fun BuildingList(
    navController: NavController,
    buildings: List<Building>,
    onSelect: (index: Int) -> Unit = { i: Int -> },
    onAddSample: ()-> Unit = {},
    selectedIndex: Int,
){
    Column(
        modifier = Modifier
            .padding(8.dp)
    ) {
        ListActionBar(items = listOf(
            ActionChip("Building", onClick = { navController.navigate("buildings/new/form") }),
            ActionChip("Sample Building", onClick = onAddSample)
        ))

        if (buildings != null && buildings?.isNotEmpty()!!) {
            com.artbird.onsite.ui.components.List<Building>(
                buildings!!,
                selectedIndex,
                onSelect = onSelect,
                itemContent = { it, selected, index ->
                    BuildingListItem(item=it, selected=selected, index =index)
                }
            )
        }
    }
}

@Preview(
    name="Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(showBackground = true)
@Composable
fun PreviewBuildingList(){
    val buildings = listOf<Building>(
        Building(_id="1", name="Main House", notes="Two stories with walkout basement", appointment = BaseAppointment(),
            floors = listOf(
                        Floor("", "First Floor", "", rooms = listOf(
                            Room("", "Living Room", ""),
                            Room("", "Family Room", ""),
                            Room("", "Dinning Room", ""),
                            Room("", "Kitchen", ""),
                        )),
                        Floor("", "Second Floor", "", rooms = listOf(
                            Room("", "Master Bedroom", ""),
                            Room("", "Room 1", ""),
                            Room("", "Room 2", ""),
                            Room("", "Room 3", ""),
                            Room("", "Washroom1", ""),
                            Room("", "Washroom2", ""),
                        )),
            ),
        ),
        Building(_id="1", name="Garage", notes="one story garage", appointment = BaseAppointment(),
            floors = listOf(
                Floor("", "First Floor", "", rooms = listOf(
                    Room("", "Garage 1", ""),
                    Room("", "Garage 2", ""),
                )),
            ),
        ),
    )

//    var address = Address("2", "", "235", "Front St", "Toronto", "ON", "L3R 0C7")

    SLTheme {
        BuildingList(
            rememberNavController(),
            buildings,
            selectedIndex = 1
        )
    }
}