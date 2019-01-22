using System;
using System.Collections.Generic;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Numerics;
using System.Threading.Tasks;
using SixLabors.ImageSharp;
using SixLabors.ImageSharp.PixelFormats;

namespace Tracer.ImageSharp
{

    public static class ImageSharpRenderer
    {
        public static void RenderTo(this Scene scene, Image<Rgba32> image)
        {
            var v = new RectangleF(0, 0, image.Width, image.Height);
            var w = new RectangleF(-2.0f, 2.0f, 4.0f, 4.0f);
            var v2w = new ViewportToWindowTransform(w, v);

            var viewing = Viewing.ViewingTransform(
                scene.Eye,
                scene.Center,
                scene.Up
            );

            image.RenderTo(v2w, (float)scene.ViewingDistance, viewing, scene.Eye,
                scene.Objects, scene.Lights, scene.AmbientLight);
        }

        public static void RenderTo(this Image<Rgba32> image, IViewportToWindowTransform v2w, float viewDistance, 
            Matrix4x4 viewing, Vector3 eye, 
            IEnumerable<Traceable> objects, IEnumerable<Light> lights, Color ambientLight)
        {
            var sw = new System.Diagnostics.Stopwatch();
            sw.Start();
            
            Parallel.For(0, image.Height, (y, state) =>
            {
                Ray ray;
                Vector3 viewingPoint;
                Vector3 worldPoint;
                Color color;
                PointF viewportPoint;
                PointF windowPoint = new PointF(0, 0);

                for (var x = 0; x < image.Width; x++)
                {
                    viewportPoint = new PointF(x, y);
                    windowPoint = v2w.Transform(viewportPoint);
                    viewingPoint = new Vector3(windowPoint.X, windowPoint.Y, viewDistance);
                    worldPoint = viewing.Image(viewingPoint);
                    ray = Ray.CreateFromPoints(eye, worldPoint);
                    color = ray.Trace(objects, lights, eye, ambientLight);
                    image[x,y] = new Rgba32(color.R, color.G, color.B);
                }
            });

            Console.WriteLine("Elapsed: " + sw.Elapsed);
        }
    }
}
