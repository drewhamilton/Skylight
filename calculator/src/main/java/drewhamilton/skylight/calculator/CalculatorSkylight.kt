package drewhamilton.skylight.calculator

import drewhamilton.skylight.Coordinates
import drewhamilton.skylight.Skylight
import drewhamilton.skylight.SkylightDay
import java.util.Date
import javax.inject.Inject

class CalculatorSkylight @Inject constructor(
    private val calculator: SkylightCalculator
) : Skylight {

    override fun getSkylightDay(coordinates: Coordinates, date: Date): SkylightDay =
        calculator.calculateSkylightInfo(date, coordinates.latitude, coordinates.longitude)
}
