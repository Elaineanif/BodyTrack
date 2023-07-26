package com.leaf3stones.tracker.mainpage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewmodel.compose.viewModel

class CalculateFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {

                    CalculateScreen()
                }
            }
        }
    }
}

@Composable
fun CalculateScreen() {
    val viewModel: CalculateViewModel = viewModel()
    val allCategory = viewModel.allCategory
    val totalCal = viewModel.totalCal

    Column(modifier = Modifier.fillMaxWidth()) {
        LazyColumn(modifier = Modifier.weight(0.8f)) {
            allCategory.forEachIndexed { index, foodCategory ->
                item {
                    Text(
                        text = foodCategory.category_name,
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                items(foodCategory.category_detail.size) { foodInfoIndex ->
                    FoodDetail(foodInfo = foodCategory.category_detail[foodInfoIndex], onClick = {
                        viewModel.onItemSelected(index, foodInfoIndex)
                    })
                }
            }
        }
        TotalCalScreen(
            totalCal = totalCal,
            modifier = Modifier.weight(0.2f),
            onClearButtonClicked = { viewModel.onClearClicked() })
    }
}


@Composable
fun TotalCalScreen(modifier: Modifier, totalCal: Int, onClearButtonClicked: () -> Unit) {
    Row(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.weight(0.7f), verticalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = "Total Calories are:",
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                text = "$totalCal Cal",
                textAlign = TextAlign.Center,
                fontStyle = FontStyle.Italic,
                fontSize = 36.sp
            )
        }
        Button(
            onClick = { onClearButtonClicked() },
            modifier = Modifier
                .weight(0.3f)
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Text(text = "Clear", textAlign = TextAlign.Center, fontSize = 24.sp)
        }
    }

}


@Composable
fun FoodDetail(foodInfo: FoodInfo, onClick: () -> Unit) {


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        contentColor = if (foodInfo.selected) {
            Color.Red
        } else {
            Color.Black
        }
    ) {
        Row(Modifier.padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = foodInfo.food_name, fontSize = 16.sp)
            Text(text = "${foodInfo.count} Cal", modifier = Modifier.padding(horizontal = 24.dp))
        }
    }
}


private val data = """
    [{"category_name": "Bread", "category_detail": [{"food_name": "Bagel ", "count": 310}, {"food_name": "Biscuit ", "count": 480}, {"food_name": "Jaffa cake ", "count": 370}, {"food_name": "Bread white ", "count": 240}, {"food_name": "Bread wholemeal ", "count": 220}, {"food_name": "Chapatis ", "count": 300}, {"food_name": "Cornflakes ", "count": 370}, {"food_name": "Crackerbread ", "count": 325}, {"food_name": "Cream crackers ", "count": 440}, {"food_name": "Crumpets ", "count": 198}, {"food_name": "Flapjacks ", "count": 500}, {"food_name": "Macaroni ", "count": 95}, {"food_name": "Muesli ", "count": 390}, {"food_name": "Naan bread ", "count": 320}, {"food_name": "Noodles ", "count": 70}, {"food_name": "Pasta ", "count": 110}, {"food_name": "Pasta ", "count": 105}, {"food_name": "Porridge ", "count": 55}, {"food_name": "Potatoes**(boiled) ", "count": 70}, {"food_name": "Potatoes**(roast) ", "count": 140}, {"food_name": "Rice (white boiled) ", "count": 140}, {"food_name": "Rice (egg-fried) ", "count": 200}, {"food_name": "Rice ( Brown ) ", "count": 135}, {"food_name": "Spaghetti (boiled) ", "count": 101}]}, {"category_name": "Meat and fish", "category_detail": [{"food_name": "Anchovies tinned ", "count": 300}, {"food_name": "Bacon average fried ", "count": 500}, {"food_name": "Bacon average grilled ", "count": 380}, {"food_name": "Beef (roast) ", "count": 280}, {"food_name": "Beef burgers ", "count": 280}, {"food_name": "Chicken ", "count": 200}, {"food_name": "Cockles ", "count": 50}, {"food_name": "Cod fresh ", "count": 100}, {"food_name": "Cod chip shop food ", "count": 200}, {"food_name": "Crab fresh ", "count": 110}, {"food_name": "Duck roast ", "count": 430}, {"food_name": "Fish cake ", "count": 200}, {"food_name": "Fish fingers ", "count": 220}, {"food_name": "Gammon ", "count": 280}, {"food_name": "Haddock fresh ", "count": 110}, {"food_name": "Halibut fresh ", "count": 125}, {"food_name": "Ham ", "count": 240}, {"food_name": "Herring fresh grilled ", "count": 200}, {"food_name": "Kidney ", "count": 160}, {"food_name": "Kipper ", "count": 120}, {"food_name": "Liver ", "count": 150}, {"food_name": "Liver pate ", "count": 300}, {"food_name": "Lamb (roast) ", "count": 300}, {"food_name": "Lobster boiled ", "count": 100}, {"food_name": "Luncheon meat ", "count": 400}, {"food_name": "Mackeral ", "count": 300}, {"food_name": "Mussels ", "count": 90}, {"food_name": "Pheasant roast ", "count": 200}, {"food_name": "Pilchards (tinned) ", "count": 140}, {"food_name": "Prawns ", "count": 100}, {"food_name": "Pork ", "count": 290}, {"food_name": "Pork pie ", "count": 450}, {"food_name": "Rabbit ", "count": 180}, {"food_name": "Salmon fresh ", "count": 180}, {"food_name": "Sardines tinned in oil ", "count": 220}, {"food_name": "Sardines in tomato sauce ", "count": 180}, {"food_name": "Sausage pork fried ", "count": 320}, {"food_name": "Sausage pork grilled ", "count": 280}, {"food_name": "Sausage roll ", "count": 480}, {"food_name": "Scampi fried in oil ", "count": 340}, {"food_name": "Steak & kidney pie ", "count": 350}, {"food_name": "Taramasalata ", "count": 490}, {"food_name": "Trout fresh ", "count": 120}, {"food_name": "Tuna tinned water ", "count": 100}, {"food_name": "Tuna tinned oil ", "count": 180}, {"food_name": "Turkey ", "count": 160}, {"food_name": "Veal ", "count": 240}]}, {"category_name": "Fruit and vegetables", "category_detail": [{"food_name": "Apple ", "count": 44}, {"food_name": "Banana ", "count": 65}, {"food_name": "Beans baked beans ", "count": 80}, {"food_name": "Beans dried (boiled) ", "count": 130}, {"food_name": "Blackberries ", "count": 25}, {"food_name": "Blackcurrant ", "count": 30}, {"food_name": "Broccoli ", "count": 32}, {"food_name": "Cabbage (boiled) ", "count": 20}, {"food_name": "Carrot (boiled) ", "count": 25}, {"food_name": "Cauliflower (boiled) ", "count": 30}, {"food_name": "Celery (boiled) ", "count": 10}, {"food_name": "Cherry ", "count": 50}, {"food_name": "Courgette ", "count": 20}, {"food_name": "Cucumber ", "count": 10}, {"food_name": "Dates ", "count": 235}, {"food_name": "Grapes ", "count": 62}, {"food_name": "Grapefruit ", "count": 32}, {"food_name": "Kiwi ", "count": 50}, {"food_name": "Leek (boiled) ", "count": 20}, {"food_name": "Lentils (boiled) ", "count": 100}, {"food_name": "Lettuce ", "count": 15}, {"food_name": "Melon ", "count": 28}, {"food_name": "Mushrooms(raw) ", "count": 15}, {"food_name": "Mushrooms (boiled) ", "count": 12}, {"food_name": "Mushrooms (fried) ", "count": 145}, {"food_name": "Olives ", "count": 80}, {"food_name": "Onion (boiled) ", "count": 18}, {"food_name": "Onion (fried) ", "count": 155}, {"food_name": "Orange ", "count": 30}, {"food_name": "Peas ", "count": 148}, {"food_name": "Peas(dried & boiled) ", "count": 120}, {"food_name": "Peach ", "count": 30}, {"food_name": "Pear ", "count": 38}, {"food_name": "Pepper yellow ", "count": 16}, {"food_name": "Pineapple ", "count": 40}, {"food_name": "Plum ", "count": 39}, {"food_name": "Spinach ", "count": 8}, {"food_name": "Strawberries (1 average) ", "count": 30}, {"food_name": "Sweetcorn ", "count": 130}, {"food_name": "Sweetcorn on the cob ", "count": 70}, {"food_name": "Tomato ", "count": 20}, {"food_name": "Tomato cherry ", "count": 17}, {"food_name": "Tomato puree ", "count": 70}, {"food_name": "Watercress ", "count": 20}]}, {"category_name": "Milk and dairy", "category_detail": [{"food_name": "Cheese average ", "count": 440}, {"food_name": "Cottage cheese low fat ", "count": 80}, {"food_name": "Cottage cheese ", "count": 98}, {"food_name": "Cream cheese ", "count": 428}, {"food_name": "Cream fresh half ", "count": 160}, {"food_name": "Cream fresh single ", "count": 200}, {"food_name": "Cream fresh double ", "count": 430}, {"food_name": "Cream fresh clotted ", "count": 600}, {"food_name": "Custard ", "count": 100}, {"food_name": "Eggs ( 1 average size) ", "count": 150}, {"food_name": "Eggs fried ", "count": 180}, {"food_name": "Fromage frais ", "count": 125}, {"food_name": "Ice cream ", "count": 180}, {"food_name": "Milk whole ", "count": 70}, {"food_name": "Milk semi-skimmed ", "count": 50}, {"food_name": "Milk skimmed ", "count": 38}, {"food_name": "Milk Soya ", "count": 36}, {"food_name": "Mousse flavored ", "count": 140}, {"food_name": "Omelette with cheese ", "count": 266}, {"food_name": "Trifle with cream ", "count": 190}, {"food_name": "Yogurt natural ", "count": 60}, {"food_name": "Yogurt reduced fat ", "count": 45}]}]
""".trimIndent()


