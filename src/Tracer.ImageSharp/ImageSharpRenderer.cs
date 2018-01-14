using System;
using System.Collections.Generic;
using System.Drawing;
using System.IO;
using System.Linq;
using MathNet.Numerics.LinearAlgebra;
using SixLabors.ImageSharp;

namespace Tracer.ImageSharp
{

    public static class ImageSharpRenderer
    {
        public static void RenderTo(this Image<Rgba32> image, IViewPortToWindowTransform v2w, double viewDistance, Matrix<double> viewing, Vector<double> eye, IEnumerable<Traceable> objects, IEnumerable<Light> lights)
        {
            Ray ray;
            Vector<double> viewingPoint;
            Vector<double> worldPoint;
            Vector<double> color;
            PointF viewportPoint;
            PointF windowPoint = new PointF(0, 0);

            for (var y = 0; y < image.Height; y++)
                for (var x = 0; x < image.Width; x++)
                {
                    viewportPoint = new PointF(x, y);
                    windowPoint = v2w.Transform(viewportPoint);
                    viewingPoint = Vector.CreateVector3(windowPoint.X, windowPoint.Y, viewDistance);
                    worldPoint = viewing.Image(viewingPoint);
                    ray = new Ray(eye, worldPoint);
                    color = ray.Trace(objects, lights, eye);
                    image[x,y] = color == null ? Rgba32.Black : new Rgba32((float)color[0], (float)color[1], (float)color[2]);
                }
        }
    }
}
