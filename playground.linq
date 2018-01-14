<Query Kind="Program">
  <Reference Relative="src\Tracer.ImageSharp\bin\Debug\netstandard2.0\Tracer.dll">\\Mac\Home\Projects\raytrace\src\Tracer.ImageSharp\bin\Debug\netstandard2.0\Tracer.dll</Reference>
  <Reference Relative="src\Tracer.ImageSharp\bin\Debug\netstandard2.0\Tracer.ImageSharp.dll">\\Mac\Home\Projects\raytrace\src\Tracer.ImageSharp\bin\Debug\netstandard2.0\Tracer.ImageSharp.dll</Reference>
  <NuGetReference Prerelease="true">MathNet.Numerics</NuGetReference>
  <NuGetReference Prerelease="true">SixLabors.ImageSharp</NuGetReference>
  <NuGetReference>System.Numerics.Vectors</NuGetReference>
  <Namespace>MathNet.Numerics.LinearAlgebra</Namespace>
  <Namespace>System.Drawing</Namespace>
  <Namespace>Tracer</Namespace>
  <Namespace>Tracer.ImageSharp</Namespace>
</Query>

void Main()
{		
	var v = new RectangleF(0, 0, 500, 500);
	//var w = new RectangleF(-2.0f, 1.5f, 4.0f, 3.0f);
	var w = new RectangleF(-2.0f, 2.0f, 4.0f, 4.0f);
	
	double viewDistance = 5;
	var eye = Vector.CreateVector3(0, 0, 5);
	var center = Vector.CreateVector3(0, 0, 0);
	var up = Vector.CreateVector3(0, 1, 0);
	var viewing = Viewing.ViewingTransform(eye, center, up);
	var inverseViewing = Viewing.InverseViewingTransform(viewing, eye);

	// Lights
	AmbientLight.Color = Vector.CreateVector3(0.2, 0.2, 0.2);
	var lights = new List<Light>
	{
		new PointLight { Location = Vector.CreateVector3(5, 3, 5), Color = Vector.CreateVector3(1, 1, 1) },
	};
	
	// Something to look at
	var sphere1 = new Sphere(Vector.CreateVector3(0.8, 0.8, 0), Vector.CreateVector3(1, 1, 1), 150)
	{
		Name = "Yellow Sphere",
	};
	sphere1.Model.Translate(-0.75, 0, 0);	
	var sphere2 = new Sphere(Vector.CreateVector3(0.8, 0, 0), Vector.CreateVector3(1, 1, 1), 150)
	{
		Name = "Red Sphere",
	};
	sphere2.Model.Translate(0.75, 0, 0);	

	var objects = new[] { sphere1, sphere2 };
	
	using (var outStream = new MemoryStream())
	using (var image = new SixLabors.ImageSharp.Image<SixLabors.ImageSharp.Rgba32>(500, 500))
	{
		image.RenderTo(v2w, viewDistance, viewing, eye, objects, lights);
		
		SixLabors.ImageSharp.ImageExtensions.SaveAsBmp(image, outStream);
		System.Drawing.Image.FromStream(outStream).Dump();
	}
}

class AffineTransform : IViewPortToWindowTransform
{
	private System.Drawing.Drawing2D.Matrix _m;
	
	public AffineTransform(RectangleF w, RectangleF v)
	{
		var widthRatio = w.Width / v.Width;
		var heightRatio = w.Height / v.Height;
		
		_m = new System.Drawing.Drawing2D.Matrix(widthRatio, 0, 0, -heightRatio, w.X - widthRatio * v.X, w.Y + heightRatio * v.Y);		
	}
	
	public PointF Transform(PointF p1)
	{
		var points = new [] { p1 };
		_m.TransformPoints(points);
		return points[0];
	}
}

public class Scene
{
	public SizeF Viewport { get; set; }
	public RectangleF Window { get; set; }
	public double ViewingDistance { get; set; }
	public Vector<double> Eye { get; set; }
	public Vector<double> Center { get; set; } = Vector.CreateVector3(0, 0, 0);
	public Vector<double> Up { get; set; } = Vector.CreateVector3(0, 1, 0);
	public Vector<double> AmbientLight { get; set; }
	public List<Light> Lights { get; set; }
	public List<Traceable> Objects { get; set; }
}

public static class Util2
{
	
}
