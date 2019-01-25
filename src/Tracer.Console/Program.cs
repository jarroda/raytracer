using System;
using System.Collections.Generic;
using System.Drawing;
using System.IO;
using System.Numerics;
using System.Threading.Tasks;
using SixLabors.ImageSharp;
using SixLabors.ImageSharp.PixelFormats;
using Tracer.ImageSharp;
using Tracer.Objects;

namespace Tracer
{
    class Program
    {
        static void Main(string[] args)
        {
            var sphere1 = new Sphere(
                Color.Green,
                Color.White, 150)
            {
                Name = "Green Sphere",
            };
            sphere1.Model.Translate(-0.75f, 0, 0.0f);
            //sphere1.Model.Translate(0, 1.0f, 0);

            var sphere2 = new Sphere(
                Color.Red,
                Color.White, 150)
            {
                Name = "Red Sphere",
            };
            sphere2.Model.Translate(0.75f, 0, 0);

            var plane = new Objects.Plane(Color.Orange);
            plane.Model.Translate(0, -0.5f, 0);

            var scene = new Scene
            {
                Center = new Vector3(0, 0, 0),
                Eye = new Vector3(0, 0, 5),
                Up = new Vector3(0, 1, 0),
                ViewingDistance = 5,
                AmbientLight = new Vector3(0.2f, 0.2f, 0.2f).ToColor(),
                Lights = new List<Light>
                {
                    new PointLight { Location = new Vector3(5, 3, 5) },
                },
                Objects = new List<Traceable> 
                { 
                    sphere1, 
                    sphere2, 
                    // plane 
                },
            };

            // using (var outStream = File.OpenWrite("rendered.png"))
            // using (var image = new Image<Rgba32>(1000, 1000))
            // {
            //     scene.RenderTo(image);
            //     image.SaveAsPng(outStream);
            // }

            using (var outStream = File.OpenWrite("rendered.ppm"))
            {
                var image = new PpmImage(1000, 1000);
                scene.RenderTo(image);
                image.GammaCorrect(1.8f);
                image.Save(outStream).Wait();
            }
        }
    }

    public static class PPMRenderer
    {
        public static void RenderTo(this Scene scene, PpmImage image)
        {
            var v = new RectangleF(0, 0, image.Width, image.Height);
            var w = new RectangleF(-2.0f, 2.0f, 4.0f, 4.0f);
            //var w = new RectangleF(-4.0f, 4.0f, 8.0f, 8.0f);
            var v2w = new ViewportToWindowTransform(w, v);

            var viewing = Viewing.ViewingTransform(
                scene.Eye,
                scene.Center,
                scene.Up
            );

            image.RenderTo(v2w, (float)scene.ViewingDistance, viewing, scene.Eye,
                scene.Objects, scene.Lights, scene.AmbientLight);
        }

        public static void RenderTo(this PpmImage image, IViewportToWindowTransform v2w, float viewDistance, 
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
                    image.Set(y, x, color);
                }
            });

            Console.WriteLine("Elapsed: " + sw.Elapsed);
        }
    }
}
