using System;
using System.Collections.Generic;
using System.Drawing;
using System.IO;
using System.Numerics;
using System.Threading.Tasks;
using SixLabors.ImageSharp;
using SixLabors.ImageSharp.PixelFormats;
using Tracer.ImageSharp;
// using Tracer.Objects;
using Tracer.Traceables;

namespace Tracer
{
    class Program
    {
        static void Main(string[] args)
        {
            int x = 200;
            int y = 100;
            
            var lower_left = new Vector3(-2.0f, -1.0f, -1.0f);
            var horizontal = new Vector3(4.0f, 0.0f, 0.0f);
            var vertical = new Vector3(0.0f, 2.0f, 0.0f);
            var origin = new Vector3(0.0f, 0.0f, 0.0f);
            var image = new PpmImage(x, y);

            var traceables = new Traceable[]
            {
                new Sphere(new Vector3(0,0,-1), 0.5f),
                new Sphere(new Vector3(0, -100.5f, -1), 100),
            };

            var outStream = File.OpenWrite("rendered.ppm");
            var writer = new StreamWriter(outStream);
                writer.WriteLine("P3");
                writer.WriteLine($"{x} {y}");
                writer.WriteLine("255");

                Color color;
                Ray ray;

                for (int j = y-1; j >= 0; j--)
                for (int i = 0; i < x; i++)
                {
                    var u = (float)i / x;
                    var v = (float)j / y;

                    ray = Ray.Create(origin, lower_left + u * horizontal + v * vertical);
                    color = Col(ray, traceables).ToColor();
                    writer.WriteLine($"{color.R} {color.G} {color.B}");
                }
                // image.GammaCorrect(1.8f);
                image.Save(outStream).Wait();
        }

        static Vector3 Col(Ray r, IEnumerable<Traceable> traceables)
        {
            HitRecord hit;

            if (Hit(traceables, r, 0.0f, float.MaxValue, out hit))
            {
                return 0.5f * new Vector3(hit.Normal.X + 1, hit.Normal.Y + 1, hit.Normal.Z + 1);
            }
            else
            {
                var unit_direction = r.Direction.UnitVector();
                var t = 0.5f * (unit_direction.Y + 1.0f);
                return (1.0f - t) * new Vector3(1.0f, 1.0f, 1.0f) + t * new Vector3(0.5f, 0.7f, 1.0f);
            }
        }

        static bool Hit(IEnumerable<Traceable> traceables, Ray r, float tmin, float tmax, out HitRecord hit)
        {
            var hitAnything = false;
            var closest = tmax;
            hit = new HitRecord();

            foreach (var traceable in traceables)
            {
                if (traceable.Hit(r, tmin, closest, out HitRecord tempHit))
                {
                    hitAnything = true;
                    closest = tempHit.T;
                    hit = tempHit;
                }
            }
            return hitAnything;
        }

        // static void Run()
        // {
        //     var sphere1 = new Sphere(
        //         Color.Green,
        //         Color.White, 150)
        //     {
        //         Name = "Green Sphere",
        //     };
        //     sphere1.Model.Translate(-0.75f, 0, 0.0f);
        //     //sphere1.Model.Translate(0, 1.0f, 0);

        //     var sphere2 = new Sphere(
        //         Color.Red,
        //         Color.White, 150)
        //     {
        //         Name = "Red Sphere",
        //     };
        //     sphere2.Model.Translate(0.75f, 0, 0);

        //     var plane = new Objects.Plane(Color.Orange);
        //     plane.Model.Translate(0, -0.5f, 0);

        //     var scene = new Scene
        //     {
        //         Center = new Vector3(0, 0, 0),
        //         Eye = new Vector3(0, 0, 5),
        //         Up = new Vector3(0, 1, 0),
        //         ViewingDistance = 5,
        //         AmbientLight = new Vector3(0.2f, 0.2f, 0.2f).ToColor(),
        //         Lights = new List<Light>
        //         {
        //             new PointLight { Location = new Vector3(5, 3, 5) },
        //         },
        //         Objects = new List<Traceable> 
        //         { 
        //             sphere1, 
        //             sphere2, 
        //             // plane 
        //         },
        //     };

        //     // using (var outStream = File.OpenWrite("rendered.png"))
        //     // using (var image = new Image<Rgba32>(1000, 1000))
        //     // {
        //     //     scene.RenderTo(image);
        //     //     image.SaveAsPng(outStream);
        //     // }

        //     using (var outStream = File.OpenWrite("rendered.ppm"))
        //     {
        //         var image = new PpmImage(1000, 1000);
        //         scene.RenderTo(image);
        //         image.GammaCorrect(1.8f);
        //         image.Save(outStream).Wait();
        //     }
        // }
    }

    // public static class PPMRenderer
    // {
    //     public static void RenderTo(this Scene scene, PpmImage image)
    //     {
    //         var v = new RectangleF(0, 0, image.Width, image.Height);
    //         var w = new RectangleF(-2.0f, 2.0f, 4.0f, 4.0f);
    //         //var w = new RectangleF(-4.0f, 4.0f, 8.0f, 8.0f);
    //         var v2w = new ViewportToWindowTransform(w, v);

    //         var viewing = Viewing.ViewingTransform(
    //             scene.Eye,
    //             scene.Center,
    //             scene.Up
    //         );

    //         image.RenderTo(v2w, (float)scene.ViewingDistance, viewing, scene.Eye,
    //             scene.Objects, scene.Lights, scene.AmbientLight);
    //     }

    //     public static void RenderTo(this PpmImage image, IViewportToWindowTransform v2w, float viewDistance, 
    //         Matrix4x4 viewing, Vector3 eye, 
    //         IEnumerable<Traceable> objects, IEnumerable<Light> lights, Color ambientLight)
    //     {
    //         var sw = new System.Diagnostics.Stopwatch();
    //         sw.Start();
            
    //         Parallel.For(0, image.Height, (y, state) =>
    //         {
    //             Ray ray;
    //             Vector3 viewingPoint;
    //             Vector3 worldPoint;
    //             Color color;
    //             PointF viewportPoint;
    //             PointF windowPoint = new PointF(0, 0);

    //             for (var x = 0; x < image.Width; x++)
    //             {
    //                 viewportPoint = new PointF(x, y);
    //                 windowPoint = v2w.Transform(viewportPoint);
    //                 viewingPoint = new Vector3(windowPoint.X, windowPoint.Y, viewDistance);
    //                 worldPoint = viewing.Image(viewingPoint);
    //                 ray = Ray.CreateFromPoints(eye, worldPoint);
    //                 color = ray.Trace(objects, lights, eye, ambientLight);
    //                 image.Set(y, x, color);
    //             }
    //         });

    //         Console.WriteLine("Elapsed: " + sw.Elapsed);
    //     }
    // }
}
