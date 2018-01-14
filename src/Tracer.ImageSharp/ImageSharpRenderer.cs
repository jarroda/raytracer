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
                    image[x,y] = ray.Trace(objects, lights, eye);
                }
        }

        public static Rgba32 Trace(this Ray ray, IEnumerable<Traceable> objects, IEnumerable<Light> lights, Vector<double> eye)
        {
            if (objects == null)
            {
                throw new ArgumentNullException(nameof(objects));
            }
            if (lights == null)
            {
                throw new ArgumentNullException(nameof(lights));
            }
            if (eye == null)
            {
                throw new ArgumentNullException(nameof(eye));
            }

            double hit;
            double[] hits;
            var nearestHit = Util.NearlyInfinite;
            Traceable nearestObject = null;

            foreach (var t in objects)
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
                return Rgba32.Black;
            }
            else
            {
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
                return new Rgba32((float)color[0], (float)color[1], (float)color[2]);
            }
        }
    }
}
