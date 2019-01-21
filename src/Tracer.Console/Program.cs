using System;
using System.Collections.Generic;
using System.Drawing;
using System.IO;
using SixLabors.ImageSharp;
using SixLabors.ImageSharp.PixelFormats;
using Tracer.ImageSharp;

namespace Tracer
{
    class Program
    {
        static void Main(string[] args)
        {
            AmbientLight.Color = Vector.CreateVector3(0.2, 0.2, 0.2);
            // AmbientLight.Color = Color.DimGray.ToVector();

            var sphere1 = new Sphere(
                Color.LightBlue,
                Color.White, 150)
            {
                Name = "Yellow Sphere",
            };
            sphere1.Model.Translate(-0.75, 0, 0.0);	
            var sphere2 = new Sphere(
                Color.Red,
                Color.White, 150)
            {
                Name = "Red Sphere",
            };
            sphere2.Model.Translate(0.75, 0, 0);

            var scene = new Scene
            {
                Center = Vector.CreateVector3(0, 0, 0),
                Eye = Vector.CreateVector3(0, 0, 5),
                Up = Vector.CreateVector3(0, 1, 0),
                ViewingDistance = 5,
                Lights = new List<Light>
                {
                    new PointLight { Location = Vector.CreateVector3(5, 3, 5), Color = Vector.CreateVector3(1, 1, 1) },
                },
                Objects = new List<Traceable> { sphere1, sphere2 },
            };

            using (var outStream = File.OpenWrite("quack.png"))
            using (var image = new Image<Rgba32>(1000, 1000))
            {
                scene.RenderTo(image);

                image.SaveAsPng(outStream);
            }
        }
    }
}
