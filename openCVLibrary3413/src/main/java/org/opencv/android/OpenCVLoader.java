package org.opencv.android;

import android.content.Context;

public class OpenCVLoader
{
    public static final String OPENCV_VERSION_3_0_0 = "3.0.0";

    /**
     * Loads and initializes OpenCV library from current application package. Roughly, it's an analog of system.loadLibrary("opencv_java").
     * @return Returns true is initialization of OpenCV was successful.
     */
    public static boolean initDebug()
    {
        return StaticHelper.initOpenCV(false);
    }

    /**
     * Loads and initializes OpenCV library using OpenCV Engine service.
     * @param Version OpenCV library version.
     * @param AppContext application context for connecting to the service.
     * @param Callback object, that implements LoaderCallbackInterface for handling the connection status.
     * @return Returns true if initialization of OpenCV is successful.
     */
    public static boolean initAsync(String Version, Context AppContext,
            LoaderCallbackInterface Callback)
    {
        return AsyncServiceHelper.initOpenCV(Version, AppContext, Callback);
    }
}
