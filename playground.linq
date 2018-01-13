<Query Kind="Program">
  <Reference Relative="src\Tracer\bin\Debug\netstandard2.0\Tracer.dll">\\Mac\Home\Projects\raytrace\src\Tracer\bin\Debug\netstandard2.0\Tracer.dll</Reference>
  <NuGetReference Prerelease="true">MathNet.Numerics</NuGetReference>
  <NuGetReference Prerelease="true">SixLabors.ImageSharp</NuGetReference>
  <NuGetReference Prerelease="true">SixLabors.ImageSharp.Drawing</NuGetReference>
  <NuGetReference>System.Numerics.Vectors</NuGetReference>
  <Namespace>MathNet.Numerics.LinearAlgebra</Namespace>
  <Namespace>System.Drawing</Namespace>
  <Namespace>Tracer</Namespace>
</Query>

void Main()
{		
	var v = new RectangleF(0, 0, 500, 500);
	//var w = new RectangleF(-2.0f, 1.5f, 4.0f, 3.0f);
	var w = new RectangleF(-2.0f, 2.0f, 4.0f, 4.0f);
	
	var v2w = ViewPortToWindow(w, v);
	
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
	sphere1.Model.Translate(0.75, 0, 0);
	var objects = new[] { /*sphere1,*/ sphere2 };
	
	// Background color
	var background = Color.Blue;
	
	Ray ray;
	Vector<double> viewingPoint;
	Vector<double> worldPoint;	
	PointF viewportPoint;
	PointF windowPoint = new PointF(0, 0);
	Color color;


	using (var image = new System.Drawing.Bitmap(500, 500, System.Drawing.Imaging.PixelFormat.Format24bppRgb))
	{
//		int y = 50;
//		int x = 50;
		for (var y = 0; y < v.Height; y++)
		for (var x = 0; x < v.Width; x++)
		{
			viewportPoint = new PointF(x, y);
			windowPoint = v2w.Transform(viewportPoint);
			viewingPoint = Vector.CreateVector3(windowPoint.X, windowPoint.Y, viewDistance);
			worldPoint = viewing.Image(viewingPoint);
			ray = new Ray(eye, worldPoint);
			color = Util2.TraceARay(ray, objects, lights, eye);
			
			image.SetPixel(x, y, color);
		}
		
		image.Dump();
	}

	//	using (var outStream = new MemoryStream())
//	using (var image = new Image<Rgba32>(500, 500))
//	{
//		LinearLineSegment linerSegemnt = new LinearLineSegment(
//			new Vector2(10, 10),
//			new Vector2(200, 150),
//			new Vector2(50, 300));
//		CubicBezierLineSegment bazierSegment = new CubicBezierLineSegment(new Vector2(50, 300),
//			new Vector2(500, 500),
//			new Vector2(60, 10),
//			new Vector2(10, 400));
//
//		var p = new SixLabors.Shapes.Path(linerSegemnt, bazierSegment);
//
//		image.Mutate(x => x
//			.BackgroundColor(Rgba32.Blue)
//			
//			.Draw(Rgba32.HotPink, 5, p));
//		var data = image.SavePixelData();
//
//		var image = new Image<TPixel>(10, 10);
//		using (PixelAccessor<TPixel> pixels = image.Lock())
//		{
//			for (int i = 0; i < 10; i++)
//			{
//				for (int j = 0; j < 10; j++)
//				{
//					var v = new Vector4(i, j, 0, 1);
//					v /= 10;
//
//					var color = default(TPixel);
//					color.PackFromVector4(v);
//
//					pixels[i, j] = color;
//				}
//			}
//		}
//		return image;
//
//		image.SaveAsPng(outStream);
//		
//		System.Drawing.Image.FromStream(outStream).Dump();
//	}
}

AffineTransform ViewPortToWindow(RectangleF w, RectangleF v)
{
	var widthRatio = w.Width / v.Width;
	var heightRatio = w.Height / v.Height;
	
	return new AffineTransform(
		widthRatio, 
		0, 
		0, 
		-heightRatio, 
		w.X - widthRatio * v.X,
		w.Y + heightRatio * v.Y);
}

class AffineTransform
{
	private System.Drawing.Drawing2D.Matrix _m;
	
	public AffineTransform(float m00, float m10, float m01, float m11, float m02, float m12)
	{		
		_m = new System.Drawing.Drawing2D.Matrix(m00, m10, m01, m11, m02, m12);		
		
	}
	
	public PointF Transform(PointF p1)
	{
		var points = new [] { p1 };
		_m.TransformPoints(points);
		return points[0];
	}
}

public static class Util2
{
	public static int ToRGB(this double val)
		=> (int)Math.Floor(val >= 1 ? 255 : val * 256.0);
	
	public static Color TraceARay(Ray ray, IEnumerable<Traceable> traceables, IEnumerable<Light> lights, Vector<double> eye)
	{
		double hit;
		double[] hits;
		var nearestHit = Tracer.Util.NearlyInfinite;
		Traceable nearestObject = null;

		foreach (var t in traceables)
		{
			hits = t.Intersections(ray);

			if (hits.Any())
			{
				hit = hits.First();
				if (hit < nearestHit)
				{
					nearestHit = hit;
					nearestObject = t;
				}
			}
		}

		if (nearestObject == null)
		{
			return Color.Black;
		}
		else
		{
			//return Color.FromArgb(nearestObject.BaseColor[0].ToRGB(), nearestObject.BaseColor[1].ToRGB(), nearestObject.BaseColor[2].ToRGB());
			var normal = nearestObject.GetNormalAt(ray.PointAt(nearestHit));
			var baseColor = nearestObject.GetBaseColorAt(ray.PointAt(nearestHit));
			var color = baseColor.TermMultiple(AmbientLight.Color);
			
			Vector<double> lightVector,	
				diffuseContribution, 
				specularContribution,
				viewingVector,
				halfVector;
			double lDotN, hDotN;
			
			foreach (var l in lights)
			{
				lightVector = l.GetLightVector(ray.PointAt(nearestHit));
				lDotN = lightVector.DotProduct(normal);	
				
				if (lDotN > 0)
				{
					// Diffuse light contribution
					diffuseContribution = baseColor.TermMultiple(l.Color).ScalarMultiple(lDotN);
					color = color.Add(diffuseContribution);
					
					// Specular light contribution
					viewingVector = eye.Subtract(nearestObject.Model.Origin.Image(ray.PointAt(nearestHit))).Normalize();
					halfVector = viewingVector.Add(lightVector).Normalize();
					hDotN = halfVector.DotProduct(normal);
					
					if (hDotN > 0)
					{
						specularContribution = nearestObject.SpecularColor.TermMultiple(l.Color)
							.ScalarMultiple(Math.Pow(hDotN, nearestObject.SpectularExponent));
						color = color.Add(specularContribution);
					}
				}
			}
			
			color.Clamp();
			return Color.FromArgb(color[0].ToRGB(), color[1].ToRGB(), color[2].ToRGB());
		}		
	}
}
