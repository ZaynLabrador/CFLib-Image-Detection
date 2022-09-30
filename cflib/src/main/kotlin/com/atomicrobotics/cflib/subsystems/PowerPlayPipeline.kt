package com.atomicrobotics.cflib.subsystems

import org.firstinspires.ftc.robotcore.external.Telemetry
import org.opencv.core.*
import org.openftc.easyopencv.OpenCvPipeline
import org.opencv.imgproc.Imgproc

class PowerPlayPipeline(t : Telemetry): OpenCvPipeline() {

    enum class TargetObject {
        FOUND,
        NOT_FOUND
    }
    var targetObject = TargetObject.NOT_FOUND
    var telemetry: Telemetry = t
    val mat = Mat()
    val ROI: Rect = Rect(Point(0.0, 0.0), Point(0.0, 0.0))

    override fun processFrame(input: Mat?): Mat? {

        Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV)

        val lowHSVRed = Scalar(0.0, 0.0, 0.0)
        val highHSVRed = Scalar(0.0, 0.0, 0.0)
        val lowHSVGreen = Scalar(0.0, 0.0, 0.0)
        val highHSVGreen = Scalar(0.0, 0.0, 0.0)

        val PERCENT_COLOR_THRESHOLD = 0.5

        Core.inRange(mat, lowHSVRed, highHSVRed, mat)

        var targetRegion: Mat = mat.submat(ROI)
        var targetRegionValue = Core.sumElems(targetRegion).`val`[0] / ROI.area() / 255

        targetRegion.release()

        telemetry.addData("Raw value", Core.sumElems(targetRegion).`val`[0].toInt())
        telemetry.addData("Percentage", Math.round(targetRegionValue * 100))

        if (targetRegionValue > PERCENT_COLOR_THRESHOLD){
            targetObject = TargetObject.FOUND
        }
        else {
            targetObject = TargetObject.NOT_FOUND
        }

        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_GRAY2RGB)
        Imgproc.rectangle(mat, ROI, Scalar(255.0, 0.0, 0.0))

        return mat
    }
}