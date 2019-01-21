using System;
using System.Collections.Generic;
using System.Drawing;
using System.IO;
using System.Numerics;
using SixLabors.ImageSharp;
using SixLabors.ImageSharp.PixelFormats;
using Tracer.ImageSharp;

namespace Tracer
{
    class Program
    {
        static void Main(string[] args)
        {
            var sphere1 = new Sphere(
                Color.LightGreen,
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
                Center = new Vector3(0, 0, 0),
                Eye = new Vector3(0, 0, 5),
                Up = new Vector3(0, 1, 0),
                ViewingDistance = 5,
                AmbientLight = new Vector3(0.2f, 0.2f, 0.2f).ToColor(),
                Lights = new List<Light>
                {
                    new PointLight { Location = new Vector3(5, 3, 5) },
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
