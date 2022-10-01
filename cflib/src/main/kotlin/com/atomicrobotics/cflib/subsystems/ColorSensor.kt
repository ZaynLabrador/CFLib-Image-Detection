package com.atomicrobotics.cflib.subsystems

import com.qualcomm.robotcore.hardware.NormalizedColorSensor
import com.qualcomm.robotcore.hardware.NormalizedRGBA
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap

class ColorSensor {

    enum class SleeveColor {
        RED,
        GREEN,
        BLUE
    }

    val gain = 18f
    private lateinit var sleeveColor: SleeveColor
    private lateinit var colors: NormalizedRGBA
    private lateinit var colorSensor: NormalizedColorSensor

    fun initialize(){
        colorSensor.gain = gain
        colorSensor = hardwareMap.get(NormalizedColorSensor::class.java, "sensor_color")
        colors = colorSensor.normalizedColors
    }

    fun readSensor(): SleeveColor{
        if (colors.blue > colors.red && colors.blue > colors.green) {
            sleeveColor = SleeveColor.BLUE
        } else if (colors.green > colors.red) {
            sleeveColor = SleeveColor.GREEN
        } else {
            sleeveColor = SleeveColor.RED
        }
        return sleeveColor;
    }
}