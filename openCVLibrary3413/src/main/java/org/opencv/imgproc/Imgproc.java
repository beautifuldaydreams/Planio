//
// This file is auto-generated. Please don't modify it!
//
package org.opencv.imgproc;

import java.util.ArrayList;
import java.util.List;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfInt4;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.core.TermCriteria;
import org.opencv.imgproc.CLAHE;
import org.opencv.imgproc.GeneralizedHoughBallard;
import org.opencv.imgproc.GeneralizedHoughGuil;
import org.opencv.imgproc.LineSegmentDetector;
import org.opencv.utils.Converters;

// C++: class Imgproc

public class Imgproc {

    // C++: enum ColorConversionCodes (cv.ColorConversionCodes)
    public static final int
            COLOR_BGR2BGRA = 0,
            COLOR_RGB2RGBA = 0,
            COLOR_BGRA2BGR = 1,
            COLOR_RGBA2RGB = 1,
            COLOR_BGR2RGBA = 2,
            COLOR_RGB2BGRA = 2,
            COLOR_RGBA2BGR = 3,
            COLOR_BGRA2RGB = 3,
            COLOR_BGR2RGB = 4,
            COLOR_RGB2BGR = 4,
            COLOR_BGRA2RGBA = 5,
            COLOR_RGBA2BGRA = 5,
            COLOR_BGR2GRAY = 6,
            COLOR_RGB2GRAY = 7,
            COLOR_GRAY2BGR = 8,
            COLOR_GRAY2RGB = 8,
            COLOR_GRAY2BGRA = 9,
            COLOR_GRAY2RGBA = 9,
            COLOR_BGRA2GRAY = 10,
            COLOR_RGBA2GRAY = 11,
            COLOR_BGR2BGR565 = 12,
            COLOR_RGB2BGR565 = 13,
            COLOR_BGR5652BGR = 14,
            COLOR_BGR5652RGB = 15,
            COLOR_BGRA2BGR565 = 16,
            COLOR_RGBA2BGR565 = 17,
            COLOR_BGR5652BGRA = 18,
            COLOR_BGR5652RGBA = 19,
            COLOR_GRAY2BGR565 = 20,
            COLOR_BGR5652GRAY = 21,
            COLOR_BGR2BGR555 = 22,
            COLOR_RGB2BGR555 = 23,
            COLOR_BGR5552BGR = 24,
            COLOR_BGR5552RGB = 25,
            COLOR_BGRA2BGR555 = 26,
            COLOR_RGBA2BGR555 = 27,
            COLOR_BGR5552BGRA = 28,
            COLOR_BGR5552RGBA = 29,
            COLOR_GRAY2BGR555 = 30,
            COLOR_BGR5552GRAY = 31,
            COLOR_BGR2XYZ = 32,
            COLOR_RGB2XYZ = 33,
            COLOR_XYZ2BGR = 34,
            COLOR_XYZ2RGB = 35,
            COLOR_BGR2YCrCb = 36,
            COLOR_RGB2YCrCb = 37,
            COLOR_YCrCb2BGR = 38,
            COLOR_YCrCb2RGB = 39,
            COLOR_BGR2HSV = 40,
            COLOR_RGB2HSV = 41,
            COLOR_BGR2Lab = 44,
            COLOR_RGB2Lab = 45,
            COLOR_BGR2Luv = 50,
            COLOR_RGB2Luv = 51,
            COLOR_BGR2HLS = 52,
            COLOR_RGB2HLS = 53,
            COLOR_HSV2BGR = 54,
            COLOR_HSV2RGB = 55,
            COLOR_Lab2BGR = 56,
            COLOR_Lab2RGB = 57,
            COLOR_Luv2BGR = 58,
            COLOR_Luv2RGB = 59,
            COLOR_HLS2BGR = 60,
            COLOR_HLS2RGB = 61,
            COLOR_BGR2HSV_FULL = 66,
            COLOR_RGB2HSV_FULL = 67,
            COLOR_BGR2HLS_FULL = 68,
            COLOR_RGB2HLS_FULL = 69,
            COLOR_HSV2BGR_FULL = 70,
            COLOR_HSV2RGB_FULL = 71,
            COLOR_HLS2BGR_FULL = 72,
            COLOR_HLS2RGB_FULL = 73,
            COLOR_LBGR2Lab = 74,
            COLOR_LRGB2Lab = 75,
            COLOR_LBGR2Luv = 76,
            COLOR_LRGB2Luv = 77,
            COLOR_Lab2LBGR = 78,
            COLOR_Lab2LRGB = 79,
            COLOR_Luv2LBGR = 80,
            COLOR_Luv2LRGB = 81,
            COLOR_BGR2YUV = 82,
            COLOR_RGB2YUV = 83,
            COLOR_YUV2BGR = 84,
            COLOR_YUV2RGB = 85,
            COLOR_YUV2RGB_NV12 = 90,
            COLOR_YUV2BGR_NV12 = 91,
            COLOR_YUV2RGB_NV21 = 92,
            COLOR_YUV2BGR_NV21 = 93,
            COLOR_YUV420sp2RGB = 92,
            COLOR_YUV420sp2BGR = 93,
            COLOR_YUV2RGBA_NV12 = 94,
            COLOR_YUV2BGRA_NV12 = 95,
            COLOR_YUV2RGBA_NV21 = 96,
            COLOR_YUV2BGRA_NV21 = 97,
            COLOR_YUV420sp2RGBA = 96,
            COLOR_YUV420sp2BGRA = 97,
            COLOR_YUV2RGB_YV12 = 98,
            COLOR_YUV2BGR_YV12 = 99,
            COLOR_YUV2RGB_IYUV = 100,
            COLOR_YUV2BGR_IYUV = 101,
            COLOR_YUV2RGB_I420 = 100,
            COLOR_YUV2BGR_I420 = 101,
            COLOR_YUV420p2RGB = 98,
            COLOR_YUV420p2BGR = 99,
            COLOR_YUV2RGBA_YV12 = 102,
            COLOR_YUV2BGRA_YV12 = 103,
            COLOR_YUV2RGBA_IYUV = 104,
            COLOR_YUV2BGRA_IYUV = 105,
            COLOR_YUV2RGBA_I420 = 104,
            COLOR_YUV2BGRA_I420 = 105,
            COLOR_YUV420p2RGBA = 102,
            COLOR_YUV420p2BGRA = 103,
            COLOR_YUV2GRAY_420 = 106,
            COLOR_YUV2GRAY_NV21 = 106,
            COLOR_YUV2GRAY_NV12 = 106,
            COLOR_YUV2GRAY_YV12 = 106,
            COLOR_YUV2GRAY_IYUV = 106,
            COLOR_YUV2GRAY_I420 = 106,
            COLOR_YUV420sp2GRAY = 106,
            COLOR_YUV420p2GRAY = 106,
            COLOR_YUV2RGB_UYVY = 107,
            COLOR_YUV2BGR_UYVY = 108,
            COLOR_YUV2RGB_Y422 = 107,
            COLOR_YUV2BGR_Y422 = 108,
            COLOR_YUV2RGB_UYNV = 107,
            COLOR_YUV2BGR_UYNV = 108,
            COLOR_YUV2RGBA_UYVY = 111,
            COLOR_YUV2BGRA_UYVY = 112,
            COLOR_YUV2RGBA_Y422 = 111,
            COLOR_YUV2BGRA_Y422 = 112,
            COLOR_YUV2RGBA_UYNV = 111,
            COLOR_YUV2BGRA_UYNV = 112,
            COLOR_YUV2RGB_YUY2 = 115,
            COLOR_YUV2BGR_YUY2 = 116,
            COLOR_YUV2RGB_YVYU = 117,
            COLOR_YUV2BGR_YVYU = 118,
            COLOR_YUV2RGB_YUYV = 115,
            COLOR_YUV2BGR_YUYV = 116,
            COLOR_YUV2RGB_YUNV = 115,
            COLOR_YUV2BGR_YUNV = 116,
            COLOR_YUV2RGBA_YUY2 = 119,
            COLOR_YUV2BGRA_YUY2 = 120,
            COLOR_YUV2RGBA_YVYU = 121,
            COLOR_YUV2BGRA_YVYU = 122,
            COLOR_YUV2RGBA_YUYV = 119,
            COLOR_YUV2BGRA_YUYV = 120,
            COLOR_YUV2RGBA_YUNV = 119,
            COLOR_YUV2BGRA_YUNV = 120,
            COLOR_YUV2GRAY_UYVY = 123,
            COLOR_YUV2GRAY_YUY2 = 124,
            COLOR_YUV2GRAY_Y422 = 123,
            COLOR_YUV2GRAY_UYNV = 123,
            COLOR_YUV2GRAY_YVYU = 124,
            COLOR_YUV2GRAY_YUYV = 124,
            COLOR_YUV2GRAY_YUNV = 124,
            COLOR_RGBA2mRGBA = 125,
            COLOR_mRGBA2RGBA = 126,
            COLOR_RGB2YUV_I420 = 127,
            COLOR_BGR2YUV_I420 = 128,
            COLOR_RGB2YUV_IYUV = 127,
            COLOR_BGR2YUV_IYUV = 128,
            COLOR_RGBA2YUV_I420 = 129,
            COLOR_BGRA2YUV_I420 = 130,
            COLOR_RGBA2YUV_IYUV = 129,
            COLOR_BGRA2YUV_IYUV = 130,
            COLOR_RGB2YUV_YV12 = 131,
            COLOR_BGR2YUV_YV12 = 132,
            COLOR_RGBA2YUV_YV12 = 133,
            COLOR_BGRA2YUV_YV12 = 134,
            COLOR_BayerBG2BGR = 46,
            COLOR_BayerGB2BGR = 47,
            COLOR_BayerRG2BGR = 48,
            COLOR_BayerGR2BGR = 49,
            COLOR_BayerBG2RGB = 48,
            COLOR_BayerGB2RGB = 49,
            COLOR_BayerRG2RGB = 46,
            COLOR_BayerGR2RGB = 47,
            COLOR_BayerBG2GRAY = 86,
            COLOR_BayerGB2GRAY = 87,
            COLOR_BayerRG2GRAY = 88,
            COLOR_BayerGR2GRAY = 89,
            COLOR_BayerBG2BGR_VNG = 62,
            COLOR_BayerGB2BGR_VNG = 63,
            COLOR_BayerRG2BGR_VNG = 64,
            COLOR_BayerGR2BGR_VNG = 65,
            COLOR_BayerBG2RGB_VNG = 64,
            COLOR_BayerGB2RGB_VNG = 65,
            COLOR_BayerRG2RGB_VNG = 62,
            COLOR_BayerGR2RGB_VNG = 63,
            COLOR_BayerBG2BGR_EA = 135,
            COLOR_BayerGB2BGR_EA = 136,
            COLOR_BayerRG2BGR_EA = 137,
            COLOR_BayerGR2BGR_EA = 138,
            COLOR_BayerBG2RGB_EA = 137,
            COLOR_BayerGB2RGB_EA = 138,
            COLOR_BayerRG2RGB_EA = 135,
            COLOR_BayerGR2RGB_EA = 136,
            COLOR_BayerBG2BGRA = 139,
            COLOR_BayerGB2BGRA = 140,
            COLOR_BayerRG2BGRA = 141,
            COLOR_BayerGR2BGRA = 142,
            COLOR_BayerBG2RGBA = 141,
            COLOR_BayerGB2RGBA = 142,
            COLOR_BayerRG2RGBA = 139,
            COLOR_BayerGR2RGBA = 140,
            COLOR_COLORCVT_MAX = 143;


    // C++: enum ConnectedComponentsTypes (cv.ConnectedComponentsTypes)
    public static final int
            CC_STAT_LEFT = 0,
            CC_STAT_TOP = 1,
            CC_STAT_WIDTH = 2,
            CC_STAT_HEIGHT = 3,
            CC_STAT_AREA = 4,
            CC_STAT_MAX = 5;


    // C++: enum ContourApproximationModes (cv.ContourApproximationModes)
    public static final int
            CHAIN_APPROX_NONE = 1,
            CHAIN_APPROX_SIMPLE = 2,
            CHAIN_APPROX_TC89_L1 = 3,
            CHAIN_APPROX_TC89_KCOS = 4;


    // C++: enum DistanceTransformLabelTypes (cv.DistanceTransformLabelTypes)
    public static final int
            DIST_LABEL_CCOMP = 0,
            DIST_LABEL_PIXEL = 1;


    // C++: enum DistanceTransformMasks (cv.DistanceTransformMasks)
    public static final int
            DIST_MASK_3 = 3,
            DIST_MASK_5 = 5,
            DIST_MASK_PRECISE = 0;


    // C++: enum DistanceTypes (cv.DistanceTypes)
    public static final int
            DIST_USER = -1,
            DIST_L1 = 1,
            DIST_L2 = 2,
            DIST_C = 3,
            DIST_L12 = 4,
            DIST_FAIR = 5,
            DIST_WELSCH = 6,
            DIST_HUBER = 7;


    // C++: enum FloodFillFlags (cv.FloodFillFlags)
    public static final int
            FLOODFILL_FIXED_RANGE = 1 << 16,
            FLOODFILL_MASK_ONLY = 1 << 17;


    // C++: enum GrabCutClasses (cv.GrabCutClasses)
    public static final int
            GC_BGD = 0,
            GC_FGD = 1,
            GC_PR_BGD = 2,
            GC_PR_FGD = 3;


    // C++: enum GrabCutModes (cv.GrabCutModes)
    public static final int
            GC_INIT_WITH_RECT = 0,
            GC_INIT_WITH_MASK = 1,
            GC_EVAL = 2,
            GC_EVAL_FREEZE_MODEL = 3;


    // C++: enum HistCompMethods (cv.HistCompMethods)
    public static final int
            HISTCMP_CORREL = 0,
            HISTCMP_CHISQR = 1,
            HISTCMP_INTERSECT = 2,
            HISTCMP_BHATTACHARYYA = 3,
            HISTCMP_HELLINGER = 3,
            HISTCMP_CHISQR_ALT = 4,
            HISTCMP_KL_DIV = 5;


    // C++: enum HoughModes (cv.HoughModes)
    public static final int
            HOUGH_STANDARD = 0,
            HOUGH_PROBABILISTIC = 1,
            HOUGH_MULTI_SCALE = 2,
            HOUGH_GRADIENT = 3;


    // C++: enum InterpolationFlags (cv.InterpolationFlags)
    public static final int
            INTER_NEAREST = 0,
            INTER_LINEAR = 1,
            INTER_CUBIC = 2,
            INTER_AREA = 3,
            INTER_LANCZOS4 = 4,
            INTER_LINEAR_EXACT = 5,
            INTER_NEAREST_EXACT = 6,
            INTER_MAX = 7,
            WARP_FILL_OUTLIERS = 8,
            WARP_INVERSE_MAP = 16;


    // C++: enum InterpolationMasks (cv.InterpolationMasks)
    public static final int
            INTER_BITS = 5,
            INTER_BITS2 = INTER_BITS * 2,
            INTER_TAB_SIZE = 1 << INTER_BITS,
            INTER_TAB_SIZE2 = INTER_TAB_SIZE * INTER_TAB_SIZE;


    // C++: enum LineSegmentDetectorModes (cv.LineSegmentDetectorModes)
    public static final int
            LSD_REFINE_NONE = 0,
            LSD_REFINE_STD = 1,
            LSD_REFINE_ADV = 2;


    // C++: enum MarkerTypes (cv.MarkerTypes)
    public static final int
            MARKER_CROSS = 0,
            MARKER_TILTED_CROSS = 1,
            MARKER_STAR = 2,
            MARKER_DIAMOND = 3,
            MARKER_SQUARE = 4,
            MARKER_TRIANGLE_UP = 5,
            MARKER_TRIANGLE_DOWN = 6;


    // C++: enum MorphShapes (cv.MorphShapes)
    public static final int
            MORPH_RECT = 0,
            MORPH_CROSS = 1,
            MORPH_ELLIPSE = 2;


    // C++: enum MorphTypes (cv.MorphTypes)
    public static final int
            MORPH_ERODE = 0,
            MORPH_DILATE = 1,
            MORPH_OPEN = 2,
            MORPH_CLOSE = 3,
            MORPH_GRADIENT = 4,
            MORPH_TOPHAT = 5,
            MORPH_BLACKHAT = 6,
            MORPH_HITMISS = 7;


    // C++: enum RectanglesIntersectTypes (cv.RectanglesIntersectTypes)
    public static final int
            INTERSECT_NONE = 0,
            INTERSECT_PARTIAL = 1,
            INTERSECT_FULL = 2;


    // C++: enum RetrievalModes (cv.RetrievalModes)
    public static final int
            RETR_EXTERNAL = 0,
            RETR_LIST = 1,
            RETR_CCOMP = 2,
            RETR_TREE = 3,
            RETR_FLOODFILL = 4;


    // C++: enum ShapeMatchModes (cv.ShapeMatchModes)
    public static final int
            CONTOURS_MATCH_I1 = 1,
            CONTOURS_MATCH_I2 = 2,
            CONTOURS_MATCH_I3 = 3;


    // C++: enum TemplateMatchModes (cv.TemplateMatchModes)
    public static final int
            TM_SQDIFF = 0,
            TM_SQDIFF_NORMED = 1,
            TM_CCORR = 2,
            TM_CCORR_NORMED = 3,
            TM_CCOEFF = 4,
            TM_CCOEFF_NORMED = 5;


    // C++: enum ThresholdTypes (cv.ThresholdTypes)
    public static final int
            THRESH_BINARY = 0,
            THRESH_BINARY_INV = 1,
            THRESH_TRUNC = 2,
            THRESH_TOZERO = 3,
            THRESH_TOZERO_INV = 4,
            THRESH_MASK = 7,
            THRESH_OTSU = 8,
            THRESH_TRIANGLE = 16;


    // C++: enum UndistortTypes (cv.UndistortTypes)
    public static final int
            PROJ_SPHERICAL_ORTHO = 0,
            PROJ_SPHERICAL_EQRECT = 1;


    // C++: enum WarpPolarMode (cv.WarpPolarMode)
    public static final int
            WARP_POLAR_LINEAR = 0,
            WARP_POLAR_LOG = 256;


    public static void Canny(Mat image, Mat edges, double threshold1, double threshold2) {
        Canny_2(image.nativeObj, edges.nativeObj, threshold1, threshold2);
    }

    public static void cornerSubPix(Mat image, Mat corners, Size winSize, Size zeroZone, TermCriteria criteria) {
        cornerSubPix_0(image.nativeObj, corners.nativeObj, winSize.width, winSize.height, zeroZone.width, zeroZone.height, criteria.type, criteria.maxCount, criteria.epsilon);
    }

    public static void warpPerspective(Mat src, Mat dst, Mat M, Size dsize, int flags, int borderMode, Scalar borderValue) {
        warpPerspective_0(src.nativeObj, dst.nativeObj, M.nativeObj, dsize.width, dsize.height, flags, borderMode, borderValue.val[0], borderValue.val[1], borderValue.val[2], borderValue.val[3]);
    }

    public static void warpPerspective(Mat src, Mat dst, Mat M, Size dsize, int flags, int borderMode) {
        warpPerspective_1(src.nativeObj, dst.nativeObj, M.nativeObj, dsize.width, dsize.height, flags, borderMode);
    }

    public static void warpPerspective(Mat src, Mat dst, Mat M, Size dsize, int flags) {
        warpPerspective_2(src.nativeObj, dst.nativeObj, M.nativeObj, dsize.width, dsize.height, flags);
    }

    public static void warpPerspective(Mat src, Mat dst, Mat M, Size dsize) {
        warpPerspective_3(src.nativeObj, dst.nativeObj, M.nativeObj, dsize.width, dsize.height);
    }

    public static void remap(Mat src, Mat dst, Mat map1, Mat map2, int interpolation, int borderMode, Scalar borderValue) {
        remap_0(src.nativeObj, dst.nativeObj, map1.nativeObj, map2.nativeObj, interpolation, borderMode, borderValue.val[0], borderValue.val[1], borderValue.val[2], borderValue.val[3]);
    }

    public static void remap(Mat src, Mat dst, Mat map1, Mat map2, int interpolation, int borderMode) {
        remap_1(src.nativeObj, dst.nativeObj, map1.nativeObj, map2.nativeObj, interpolation, borderMode);
    }

    public static void remap(Mat src, Mat dst, Mat map1, Mat map2, int interpolation) {
        remap_2(src.nativeObj, dst.nativeObj, map1.nativeObj, map2.nativeObj, interpolation);
    }

    public static void convertMaps(Mat map1, Mat map2, Mat dstmap1, Mat dstmap2, int dstmap1type, boolean nninterpolation) {
        convertMaps_0(map1.nativeObj, map2.nativeObj, dstmap1.nativeObj, dstmap2.nativeObj, dstmap1type, nninterpolation);
    }

    public static void convertMaps(Mat map1, Mat map2, Mat dstmap1, Mat dstmap2, int dstmap1type) {
        convertMaps_1(map1.nativeObj, map2.nativeObj, dstmap1.nativeObj, dstmap2.nativeObj, dstmap1type);
    }

    public static Mat getPerspectiveTransform(Mat src, Mat dst) {
        return new Mat(getPerspectiveTransform_0(src.nativeObj, dst.nativeObj));
    }

    public static Mat getAffineTransform(MatOfPoint2f src, MatOfPoint2f dst) {
        return new Mat(getAffineTransform_0(src.nativeObj, dst.nativeObj));
    }

    @Deprecated
    public static void logPolar(Mat src, Mat dst, Point center, double M, int flags) {
        logPolar_0(src.nativeObj, dst.nativeObj, center.x, center.y, M, flags);
    }

    @Deprecated
    public static void linearPolar(Mat src, Mat dst, Point center, double maxRadius, int flags) {
        linearPolar_0(src.nativeObj, dst.nativeObj, center.x, center.y, maxRadius, flags);
    }

    public static double threshold(Mat src, Mat dst, double thresh, double maxval, int type) {
        return threshold_0(src.nativeObj, dst.nativeObj, thresh, maxval, type);
    }

    public static void undistort(Mat src, Mat dst, Mat cameraMatrix, Mat distCoeffs, Mat newCameraMatrix) {
        undistort_0(src.nativeObj, dst.nativeObj, cameraMatrix.nativeObj, distCoeffs.nativeObj, newCameraMatrix.nativeObj);
    }

    /**
     * Transforms an image to compensate for lens distortion.
     *
     * The function transforms an image to compensate radial and tangential lens distortion.
     *
     * The function is simply a combination of #initUndistortRectifyMap (with unity R ) and #remap
     * (with bilinear interpolation). See the former function for details of the transformation being
     * performed.
     *
     * Those pixels in the destination image, for which there is no correspondent pixels in the source
     * image, are filled with zeros (black color).
     *
     * A particular subset of the source image that will be visible in the corrected image can be regulated
     * by newCameraMatrix. You can use #getOptimalNewCameraMatrix to compute the appropriate
     * newCameraMatrix depending on your requirements.
     *
     * The camera matrix and the distortion parameters can be determined using #calibrateCamera. If
     * the resolution of images is different from the resolution used at the calibration stage, \(f_x,
     * f_y, c_x\) and \(c_y\) need to be scaled accordingly, while the distortion coefficients remain
     * the same.
     *
     * @param src Input (distorted) image.
     * @param dst Output (corrected) image that has the same size and type as src .
     * @param cameraMatrix Input camera matrix \(A = \vecthreethree{f_x}{0}{c_x}{0}{f_y}{c_y}{0}{0}{1}\) .
     * @param distCoeffs Input vector of distortion coefficients
     * \((k_1, k_2, p_1, p_2[, k_3[, k_4, k_5, k_6[, s_1, s_2, s_3, s_4[, \tau_x, \tau_y]]]])\)
     * of 4, 5, 8, 12 or 14 elements. If the vector is NULL/empty, the zero distortion coefficients are assumed.
     * cameraMatrix but you may additionally scale and shift the result by using a different matrix.
     */
    public static void undistort(Mat src, Mat dst, Mat cameraMatrix, Mat distCoeffs) {
        undistort_1(src.nativeObj, dst.nativeObj, cameraMatrix.nativeObj, distCoeffs.nativeObj);
    }

    /**
     * Computes the undistortion and rectification transformation map.
     *
     * The function computes the joint undistortion and rectification transformation and represents the
     * result in the form of maps for remap. The undistorted image looks like original, as if it is
     * captured with a camera using the camera matrix =newCameraMatrix and zero distortion. In case of a
     * monocular camera, newCameraMatrix is usually equal to cameraMatrix, or it can be computed by
     * #getOptimalNewCameraMatrix for a better control over scaling. In case of a stereo camera,
     * newCameraMatrix is normally set to P1 or P2 computed by #stereoRectify .
     *
     * Also, this new camera is oriented differently in the coordinate space, according to R. That, for
     * example, helps to align two heads of a stereo camera so that the epipolar lines on both images
     * become horizontal and have the same y- coordinate (in case of a horizontally aligned stereo camera).
     *
     * The function actually builds the maps for the inverse mapping algorithm that is used by remap. That
     * is, for each pixel \((u, v)\) in the destination (corrected and rectified) image, the function
     * computes the corresponding coordinates in the source image (that is, in the original image from
     * camera). The following process is applied:
     * \(
     * \begin{array}{l}
     * x  \leftarrow (u - {c'}_x)/{f'}_x  \\
     * y  \leftarrow (v - {c'}_y)/{f'}_y  \\
     * {[X\,Y\,W]} ^T  \leftarrow R^{-1}*[x \, y \, 1]^T  \\
     * x'  \leftarrow X/W  \\
     * y'  \leftarrow Y/W  \\
     * r^2  \leftarrow x'^2 + y'^2 \\
     * x''  \leftarrow x' \frac{1 + k_1 r^2 + k_2 r^4 + k_3 r^6}{1 + k_4 r^2 + k_5 r^4 + k_6 r^6}
     * + 2p_1 x' y' + p_2(r^2 + 2 x'^2)  + s_1 r^2 + s_2 r^4\\
     * y''  \leftarrow y' \frac{1 + k_1 r^2 + k_2 r^4 + k_3 r^6}{1 + k_4 r^2 + k_5 r^4 + k_6 r^6}
     * + p_1 (r^2 + 2 y'^2) + 2 p_2 x' y' + s_3 r^2 + s_4 r^4 \\
     * s\vecthree{x'''}{y'''}{1} =
     * \vecthreethree{R_{33}(\tau_x, \tau_y)}{0}{-R_{13}((\tau_x, \tau_y)}
     * {0}{R_{33}(\tau_x, \tau_y)}{-R_{23}(\tau_x, \tau_y)}
     * {0}{0}{1} R(\tau_x, \tau_y) \vecthree{x''}{y''}{1}\\
     * map_x(u,v)  \leftarrow x''' f_x + c_x  \\
     * map_y(u,v)  \leftarrow y''' f_y + c_y
     * \end{array}
     * \)
     * where \((k_1, k_2, p_1, p_2[, k_3[, k_4, k_5, k_6[, s_1, s_2, s_3, s_4[, \tau_x, \tau_y]]]])\)
     * are the distortion coefficients.
     *
     * In case of a stereo camera, this function is called twice: once for each camera head, after
     * stereoRectify, which in its turn is called after #stereoCalibrate. But if the stereo camera
     * was not calibrated, it is still possible to compute the rectification transformations directly from
     * the fundamental matrix using #stereoRectifyUncalibrated. For each camera, the function computes
     * homography H as the rectification transformation in a pixel domain, not a rotation matrix R in 3D
     * space. R can be computed from H as
     * \(\texttt{R} = \texttt{cameraMatrix} ^{-1} \cdot \texttt{H} \cdot \texttt{cameraMatrix}\)
     * where cameraMatrix can be chosen arbitrarily.
     *
     * @param cameraMatrix Input camera matrix \(A=\vecthreethree{f_x}{0}{c_x}{0}{f_y}{c_y}{0}{0}{1}\) .
     * @param distCoeffs Input vector of distortion coefficients
     * \((k_1, k_2, p_1, p_2[, k_3[, k_4, k_5, k_6[, s_1, s_2, s_3, s_4[, \tau_x, \tau_y]]]])\)
     * of 4, 5, 8, 12 or 14 elements. If the vector is NULL/empty, the zero distortion coefficients are assumed.
     * @param R Optional rectification transformation in the object space (3x3 matrix). R1 or R2 ,
     * computed by #stereoRectify can be passed here. If the matrix is empty, the identity transformation
     * is assumed. In cvInitUndistortMap R assumed to be an identity matrix.
     * @param newCameraMatrix New camera matrix \(A'=\vecthreethree{f_x'}{0}{c_x'}{0}{f_y'}{c_y'}{0}{0}{1}\).
     * @param size Undistorted image size.
     * @param m1type Type of the first output map that can be CV_32FC1, CV_32FC2 or CV_16SC2, see #convertMaps
     * @param map1 The first output map.
     * @param map2 The second output map.
     */
    public static void initUndistortRectifyMap(Mat cameraMatrix, Mat distCoeffs, Mat R, Mat newCameraMatrix, Size size, int m1type, Mat map1, Mat map2) {
        initUndistortRectifyMap_0(cameraMatrix.nativeObj, distCoeffs.nativeObj, R.nativeObj, newCameraMatrix.nativeObj, size.width, size.height, m1type, map1.nativeObj, map2.nativeObj);
    }

    /**
     * Computes the ideal point coordinates from the observed point coordinates.
     *
     * The function is similar to #undistort and #initUndistortRectifyMap but it operates on a
     * sparse set of points instead of a raster image. Also the function performs a reverse transformation
     * to projectPoints. In case of a 3D object, it does not reconstruct its 3D coordinates, but for a
     * planar object, it does, up to a translation vector, if the proper R is specified.
     *
     * For each observed point coordinate \((u, v)\) the function computes:
     * \(
     * \begin{array}{l}
     * x^{"}  \leftarrow (u - c_x)/f_x  \\
     * y^{"}  \leftarrow (v - c_y)/f_y  \\
     * (x',y') = undistort(x^{"},y^{"}, \texttt{distCoeffs}) \\
     * {[X\,Y\,W]} ^T  \leftarrow R*[x' \, y' \, 1]^T  \\
     * x  \leftarrow X/W  \\
     * y  \leftarrow Y/W  \\
     * \text{only performed if P is specified:} \\
     * u'  \leftarrow x {f'}_x + {c'}_x  \\
     * v'  \leftarrow y {f'}_y + {c'}_y
     * \end{array}
     * \)
     *
     * where *undistort* is an approximate iterative algorithm that estimates the normalized original
     * point coordinates out of the normalized distorted point coordinates ("normalized" means that the
     * coordinates do not depend on the camera matrix).
     *
     * The function can be used for both a stereo camera head or a monocular camera (when R is empty).
     * @param src Observed point coordinates, 2xN/Nx2 1-channel or 1xN/Nx1 2-channel (CV_32FC2 or CV_64FC2) (or
     * vector&lt;Point2f&gt; ).
     * @param dst Output ideal point coordinates (1xN/Nx1 2-channel or vector&lt;Point2f&gt; ) after undistortion and reverse perspective
     * transformation. If matrix P is identity or omitted, dst will contain normalized point coordinates.
     * @param cameraMatrix Camera matrix \(\vecthreethree{f_x}{0}{c_x}{0}{f_y}{c_y}{0}{0}{1}\) .
     * @param distCoeffs Input vector of distortion coefficients
     * \((k_1, k_2, p_1, p_2[, k_3[, k_4, k_5, k_6[, s_1, s_2, s_3, s_4[, \tau_x, \tau_y]]]])\)
     * of 4, 5, 8, 12 or 14 elements. If the vector is NULL/empty, the zero distortion coefficients are assumed.
     * @param R Rectification transformation in the object space (3x3 matrix). R1 or R2 computed by
     * #stereoRectify can be passed here. If the matrix is empty, the identity transformation is used.
     * @param P New camera matrix (3x3) or new projection matrix (3x4) \(\begin{bmatrix} {f'}_x &amp; 0 &amp; {c'}_x &amp; t_x \\ 0 &amp; {f'}_y &amp; {c'}_y &amp; t_y \\ 0 &amp; 0 &amp; 1 &amp; t_z \end{bmatrix}\). P1 or P2 computed by
     * #stereoRectify can be passed here. If the matrix is empty, the identity new camera matrix is used.
     */
    public static void undistortPoints(Mat src, Mat dst, Mat cameraMatrix, Mat distCoeffs, Mat R, Mat P) {
        undistortPoints_0(src.nativeObj, dst.nativeObj, cameraMatrix.nativeObj, distCoeffs.nativeObj, R.nativeObj, P.nativeObj);
    }


    public static void undistortPoints(Mat src, Mat dst, Mat cameraMatrix, Mat distCoeffs, Mat R) {
        undistortPoints_1(src.nativeObj, dst.nativeObj, cameraMatrix.nativeObj, distCoeffs.nativeObj, R.nativeObj);
    }

    public static void undistortPoints(Mat src, Mat dst, Mat cameraMatrix, Mat distCoeffs) {
        undistortPoints_2(src.nativeObj, dst.nativeObj, cameraMatrix.nativeObj, distCoeffs.nativeObj);
    }


    public static void equalizeHist(Mat src, Mat dst) {
        equalizeHist_0(src.nativeObj, dst.nativeObj);
    }

    public static void cvtColor(Mat src, Mat dst, int code, int dstCn) {
        cvtColor_0(src.nativeObj, dst.nativeObj, code, dstCn);
    }

    /**
     * Converts an image from one color space to another.
     *
     * The function converts an input image from one color space to another. In case of a transformation
     * to-from RGB color space, the order of the channels should be specified explicitly (RGB or BGR). Note
     * that the default color format in OpenCV is often referred to as RGB but it is actually BGR (the
     * bytes are reversed). So the first byte in a standard (24-bit) color image will be an 8-bit Blue
     * component, the second byte will be Green, and the third byte will be Red. The fourth, fifth, and
     * sixth bytes would then be the second pixel (Blue, then Green, then Red), and so on.
     *
     * The conventional ranges for R, G, and B channel values are:
     * <ul>
     *   <li>
     *    0 to 255 for CV_8U images
     *   </li>
     *   <li>
     *    0 to 65535 for CV_16U images
     *   </li>
     *   <li>
     *    0 to 1 for CV_32F images
     *   </li>
     * </ul>
     *
     * In case of linear transformations, the range does not matter. But in case of a non-linear
     * transformation, an input RGB image should be normalized to the proper value range to get the correct
     * results, for example, for RGB \(\rightarrow\) L\*u\*v\* transformation. For example, if you have a
     * 32-bit floating-point image directly converted from an 8-bit image without any scaling, then it will
     * have the 0..255 value range instead of 0..1 assumed by the function. So, before calling #cvtColor ,
     * you need first to scale the image down:
     * <code>
     *     img *= 1./255;
     *     cvtColor(img, img, COLOR_BGR2Luv);
     * </code>
     * If you use #cvtColor with 8-bit images, the conversion will have some information lost. For many
     * applications, this will not be noticeable but it is recommended to use 32-bit images in applications
     * that need the full range of colors or that convert an image before an operation and then convert
     * back.
     *
     * If conversion adds the alpha channel, its value will set to the maximum of corresponding channel
     * range: 255 for CV_8U, 65535 for CV_16U, 1 for CV_32F.
     *
     * @param src input image: 8-bit unsigned, 16-bit unsigned ( CV_16UC... ), or single-precision
     * floating-point.
     * @param dst output image of the same size and depth as src.
     * @param code color space conversion code (see #ColorConversionCodes).
     * channels is derived automatically from src and code.
     *
     * SEE: REF: imgproc_color_conversions
     */
    public static void cvtColor(Mat src, Mat dst, int code) {
        cvtColor_1(src.nativeObj, dst.nativeObj, code);
    }

    public static void cvtColorTwoPlane(Mat src1, Mat src2, Mat dst, int code) {
        cvtColorTwoPlane_0(src1.nativeObj, src2.nativeObj, dst.nativeObj, code);
    }

    /**
     * Draws a line segment connecting two points.
     *
     * The function line draws the line segment between pt1 and pt2 points in the image. The line is
     * clipped by the image boundaries. For non-antialiased lines with integer coordinates, the 8-connected
     * or 4-connected Bresenham algorithm is used. Thick lines are drawn with rounding endings. Antialiased
     * lines are drawn using Gaussian filtering.
     *
     * @param img Image.
     * @param pt1 First point of the line segment.
     * @param pt2 Second point of the line segment.
     * @param color Line color.
     * @param thickness Line thickness.
     * @param lineType Type of the line. See #LineTypes.
     * @param shift Number of fractional bits in the point coordinates.
     */
    public static void line(Mat img, Point pt1, Point pt2, Scalar color, int thickness, int lineType, int shift) {
        line_0(img.nativeObj, pt1.x, pt1.y, pt2.x, pt2.y, color.val[0], color.val[1], color.val[2], color.val[3], thickness, lineType, shift);
    }

    /**
     * Draws a line segment connecting two points.
     *
     * The function line draws the line segment between pt1 and pt2 points in the image. The line is
     * clipped by the image boundaries. For non-antialiased lines with integer coordinates, the 8-connected
     * or 4-connected Bresenham algorithm is used. Thick lines are drawn with rounding endings. Antialiased
     * lines are drawn using Gaussian filtering.
     *
     * @param img Image.
     * @param pt1 First point of the line segment.
     * @param pt2 Second point of the line segment.
     * @param color Line color.
     * @param thickness Line thickness.
     * @param lineType Type of the line. See #LineTypes.
     */
    public static void line(Mat img, Point pt1, Point pt2, Scalar color, int thickness, int lineType) {
        line_1(img.nativeObj, pt1.x, pt1.y, pt2.x, pt2.y, color.val[0], color.val[1], color.val[2], color.val[3], thickness, lineType);
    }

    /**
     * Draws a line segment connecting two points.
     *
     * The function line draws the line segment between pt1 and pt2 points in the image. The line is
     * clipped by the image boundaries. For non-antialiased lines with integer coordinates, the 8-connected
     * or 4-connected Bresenham algorithm is used. Thick lines are drawn with rounding endings. Antialiased
     * lines are drawn using Gaussian filtering.
     *
     * @param img Image.
     * @param pt1 First point of the line segment.
     * @param pt2 Second point of the line segment.
     * @param color Line color.
     * @param thickness Line thickness.
     */
    public static void line(Mat img, Point pt1, Point pt2, Scalar color, int thickness) {
        line_2(img.nativeObj, pt1.x, pt1.y, pt2.x, pt2.y, color.val[0], color.val[1], color.val[2], color.val[3], thickness);
    }

    /**
     * Draws a line segment connecting two points.
     *
     * The function line draws the line segment between pt1 and pt2 points in the image. The line is
     * clipped by the image boundaries. For non-antialiased lines with integer coordinates, the 8-connected
     * or 4-connected Bresenham algorithm is used. Thick lines are drawn with rounding endings. Antialiased
     * lines are drawn using Gaussian filtering.
     *
     * @param img Image.
     * @param pt1 First point of the line segment.
     * @param pt2 Second point of the line segment.
     * @param color Line color.
     */
    public static void line(Mat img, Point pt1, Point pt2, Scalar color) {
        line_3(img.nativeObj, pt1.x, pt1.y, pt2.x, pt2.y, color.val[0], color.val[1], color.val[2], color.val[3]);
    }

    /**
     * Draws a simple, thick, or filled up-right rectangle.
     *
     * The function cv::rectangle draws a rectangle outline or a filled rectangle whose two opposite corners
     * are pt1 and pt2.
     *
     * @param img Image.
     * @param pt1 Vertex of the rectangle.
     * @param pt2 Vertex of the rectangle opposite to pt1 .
     * @param color Rectangle color or brightness (grayscale image).
     * @param thickness Thickness of lines that make up the rectangle. Negative values, like #FILLED,
     * mean that the function has to draw a filled rectangle.
     * @param lineType Type of the line. See #LineTypes
     * @param shift Number of fractional bits in the point coordinates.
     */
    public static void rectangle(Mat img, Point pt1, Point pt2, Scalar color, int thickness, int lineType, int shift) {
        rectangle_0(img.nativeObj, pt1.x, pt1.y, pt2.x, pt2.y, color.val[0], color.val[1], color.val[2], color.val[3], thickness, lineType, shift);
    }

    /**
     * Draws a simple, thick, or filled up-right rectangle.
     *
     * The function cv::rectangle draws a rectangle outline or a filled rectangle whose two opposite corners
     * are pt1 and pt2.
     *
     * @param img Image.
     * @param pt1 Vertex of the rectangle.
     * @param pt2 Vertex of the rectangle opposite to pt1 .
     * @param color Rectangle color or brightness (grayscale image).
     * @param thickness Thickness of lines that make up the rectangle. Negative values, like #FILLED,
     * mean that the function has to draw a filled rectangle.
     * @param lineType Type of the line. See #LineTypes
     */
    public static void rectangle(Mat img, Point pt1, Point pt2, Scalar color, int thickness, int lineType) {
        rectangle_1(img.nativeObj, pt1.x, pt1.y, pt2.x, pt2.y, color.val[0], color.val[1], color.val[2], color.val[3], thickness, lineType);
    }

    /**
     * Draws a simple, thick, or filled up-right rectangle.
     *
     * The function cv::rectangle draws a rectangle outline or a filled rectangle whose two opposite corners
     * are pt1 and pt2.
     *
     * @param img Image.
     * @param pt1 Vertex of the rectangle.
     * @param pt2 Vertex of the rectangle opposite to pt1 .
     * @param color Rectangle color or brightness (grayscale image).
     * @param thickness Thickness of lines that make up the rectangle. Negative values, like #FILLED,
     * mean that the function has to draw a filled rectangle.
     */
    public static void rectangle(Mat img, Point pt1, Point pt2, Scalar color, int thickness) {
        rectangle_2(img.nativeObj, pt1.x, pt1.y, pt2.x, pt2.y, color.val[0], color.val[1], color.val[2], color.val[3], thickness);
    }

    /**
     * Draws a simple, thick, or filled up-right rectangle.
     *
     * The function cv::rectangle draws a rectangle outline or a filled rectangle whose two opposite corners
     * are pt1 and pt2.
     *
     * @param img Image.
     * @param pt1 Vertex of the rectangle.
     * @param pt2 Vertex of the rectangle opposite to pt1 .
     * @param color Rectangle color or brightness (grayscale image).
     * mean that the function has to draw a filled rectangle.
     */
    public static void rectangle(Mat img, Point pt1, Point pt2, Scalar color) {
        rectangle_3(img.nativeObj, pt1.x, pt1.y, pt2.x, pt2.y, color.val[0], color.val[1], color.val[2], color.val[3]);
    }

    private static native void Canny_2(long image_nativeObj, long edges_nativeObj, double threshold1, double threshold2);
    private static native void cornerSubPix_0(long image_nativeObj, long corners_nativeObj, double winSize_width, double winSize_height, double zeroZone_width, double zeroZone_height, int criteria_type, int criteria_maxCount, double criteria_epsilon);
    // C++:  void cv::warpPerspective(Mat src, Mat& dst, Mat M, Size dsize, int flags = INTER_LINEAR, int borderMode = BORDER_CONSTANT, Scalar borderValue = Scalar())
    private static native void warpPerspective_0(long src_nativeObj, long dst_nativeObj, long M_nativeObj, double dsize_width, double dsize_height, int flags, int borderMode, double borderValue_val0, double borderValue_val1, double borderValue_val2, double borderValue_val3);
    private static native void warpPerspective_1(long src_nativeObj, long dst_nativeObj, long M_nativeObj, double dsize_width, double dsize_height, int flags, int borderMode);
    private static native void warpPerspective_2(long src_nativeObj, long dst_nativeObj, long M_nativeObj, double dsize_width, double dsize_height, int flags);
    private static native void warpPerspective_3(long src_nativeObj, long dst_nativeObj, long M_nativeObj, double dsize_width, double dsize_height);

    // C++:  void cv::remap(Mat src, Mat& dst, Mat map1, Mat map2, int interpolation, int borderMode = BORDER_CONSTANT, Scalar borderValue = Scalar())
    private static native void remap_0(long src_nativeObj, long dst_nativeObj, long map1_nativeObj, long map2_nativeObj, int interpolation, int borderMode, double borderValue_val0, double borderValue_val1, double borderValue_val2, double borderValue_val3);
    private static native void remap_1(long src_nativeObj, long dst_nativeObj, long map1_nativeObj, long map2_nativeObj, int interpolation, int borderMode);
    private static native void remap_2(long src_nativeObj, long dst_nativeObj, long map1_nativeObj, long map2_nativeObj, int interpolation);

    // C++:  void cv::convertMaps(Mat map1, Mat map2, Mat& dstmap1, Mat& dstmap2, int dstmap1type, bool nninterpolation = false)
    private static native void convertMaps_0(long map1_nativeObj, long map2_nativeObj, long dstmap1_nativeObj, long dstmap2_nativeObj, int dstmap1type, boolean nninterpolation);
    private static native void convertMaps_1(long map1_nativeObj, long map2_nativeObj, long dstmap1_nativeObj, long dstmap2_nativeObj, int dstmap1type);

    // C++:  Mat cv::getPerspectiveTransform(Mat src, Mat dst)
    private static native long getPerspectiveTransform_0(long src_nativeObj, long dst_nativeObj);

    // C++:  Mat cv::getAffineTransform(vector_Point2f src, vector_Point2f dst)
    private static native long getAffineTransform_0(long src_mat_nativeObj, long dst_mat_nativeObj);

    private static native void logPolar_0(long src_nativeObj, long dst_nativeObj, double center_x, double center_y, double M, int flags);

    // C++:  void cv::linearPolar(Mat src, Mat& dst, Point2f center, double maxRadius, int flags)
    private static native void linearPolar_0(long src_nativeObj, long dst_nativeObj, double center_x, double center_y, double maxRadius, int flags);

    // C++:  double cv::threshold(Mat src, Mat& dst, double thresh, double maxval, int type)
    private static native double threshold_0(long src_nativeObj, long dst_nativeObj, double thresh, double maxval, int type);

    // C++:  void cv::undistort(Mat src, Mat& dst, Mat cameraMatrix, Mat distCoeffs, Mat newCameraMatrix = Mat())
    private static native void undistort_0(long src_nativeObj, long dst_nativeObj, long cameraMatrix_nativeObj, long distCoeffs_nativeObj, long newCameraMatrix_nativeObj);
    private static native void undistort_1(long src_nativeObj, long dst_nativeObj, long cameraMatrix_nativeObj, long distCoeffs_nativeObj);

    // C++:  void cv::initUndistortRectifyMap(Mat cameraMatrix, Mat distCoeffs, Mat R, Mat newCameraMatrix, Size size, int m1type, Mat& map1, Mat& map2)
    private static native void initUndistortRectifyMap_0(long cameraMatrix_nativeObj, long distCoeffs_nativeObj, long R_nativeObj, long newCameraMatrix_nativeObj, double size_width, double size_height, int m1type, long map1_nativeObj, long map2_nativeObj);

    // C++:  void cv::undistortPoints(Mat src, Mat& dst, Mat cameraMatrix, Mat distCoeffs, Mat R = Mat(), Mat P = Mat())
    private static native void undistortPoints_0(long src_nativeObj, long dst_nativeObj, long cameraMatrix_nativeObj, long distCoeffs_nativeObj, long R_nativeObj, long P_nativeObj);
    private static native void undistortPoints_1(long src_nativeObj, long dst_nativeObj, long cameraMatrix_nativeObj, long distCoeffs_nativeObj, long R_nativeObj);
    private static native void undistortPoints_2(long src_nativeObj, long dst_nativeObj, long cameraMatrix_nativeObj, long distCoeffs_nativeObj);

    // C++:  void cv::equalizeHist(Mat src, Mat& dst)
    private static native void equalizeHist_0(long src_nativeObj, long dst_nativeObj);

    private static native void cvtColor_0(long src_nativeObj, long dst_nativeObj, int code, int dstCn);
    private static native void cvtColor_1(long src_nativeObj, long dst_nativeObj, int code);

    // C++:  void cv::cvtColorTwoPlane(Mat src1, Mat src2, Mat& dst, int code)
    private static native void cvtColorTwoPlane_0(long src1_nativeObj, long src2_nativeObj, long dst_nativeObj, int code);


    // C++:  void cv::line(Mat& img, Point pt1, Point pt2, Scalar color, int thickness = 1, int lineType = LINE_8, int shift = 0)
    private static native void line_0(long img_nativeObj, double pt1_x, double pt1_y, double pt2_x, double pt2_y, double color_val0, double color_val1, double color_val2, double color_val3, int thickness, int lineType, int shift);
    private static native void line_1(long img_nativeObj, double pt1_x, double pt1_y, double pt2_x, double pt2_y, double color_val0, double color_val1, double color_val2, double color_val3, int thickness, int lineType);
    private static native void line_2(long img_nativeObj, double pt1_x, double pt1_y, double pt2_x, double pt2_y, double color_val0, double color_val1, double color_val2, double color_val3, int thickness);
    private static native void line_3(long img_nativeObj, double pt1_x, double pt1_y, double pt2_x, double pt2_y, double color_val0, double color_val1, double color_val2, double color_val3);

    // C++:  void cv::rectangle(Mat& img, Point pt1, Point pt2, Scalar color, int thickness = 1, int lineType = LINE_8, int shift = 0)
    private static native void rectangle_0(long img_nativeObj, double pt1_x, double pt1_y, double pt2_x, double pt2_y, double color_val0, double color_val1, double color_val2, double color_val3, int thickness, int lineType, int shift);
    private static native void rectangle_1(long img_nativeObj, double pt1_x, double pt1_y, double pt2_x, double pt2_y, double color_val0, double color_val1, double color_val2, double color_val3, int thickness, int lineType);
    private static native void rectangle_2(long img_nativeObj, double pt1_x, double pt1_y, double pt2_x, double pt2_y, double color_val0, double color_val1, double color_val2, double color_val3, int thickness);
    private static native void rectangle_3(long img_nativeObj, double pt1_x, double pt1_y, double pt2_x, double pt2_y, double color_val0, double color_val1, double color_val2, double color_val3);
}
