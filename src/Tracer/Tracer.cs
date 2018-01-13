using System.Collections.Generic;
using System.Linq;

namespace Tracer
{
    public static class Tracer
    {
        // public static Color TraceARay(Ray ray, IEnumerable<Traceable> traceables, IEnumerable<Light> lights, Vector<double> eye)
        // {
        //     double hit;
        //     double[] hits;
        //     var nearestHit = Tracer.Util.NearlyInfinite;
        //     Traceable nearestObject = null;

        //     foreach (var t in traceables)
        //     {
        //         hits = t.Intersections(ray);

        //         if (hits.Any())
        //         {
        //             hit = hits.First();
        //             if (hit < nearestHit)
        //             {
        //                 nearestHit = hit;
        //                 nearestObject = t;
        //             }
        //         }
        //     }

        //     if (nearestObject == null)
        //     {
        //         return Color.Black;
        //     }
        //     else
        //     {
        //         var normal = nearestObject.GetNormalAt(ray.PointAt(nearestHit));
        //         var baseColor = nearestObject.GetBaseColorAt(ray.PointAt(nearestHit));
        //         var color = baseColor.TermMultiple(AmbientLight.Color);
                
        //         Vector<double> lightVector,	
        //             diffuseContribution, 
        //             specularContribution,
        //             viewingVector,
        //             halfVector;
        //         double lDotN, hDotN;
                
        //         foreach (var l in lights)
        //         {
        //             lightVector = l.GetLightVector(ray.PointAt(nearestHit));
        //             lDotN = lightVector.DotProduct(normal);	
                    
        //             if (lDotN > 0)
        //             {
        //                 // Diffuse light contribution
        //                 diffuseContribution = baseColor.TermMultiple(l.Color).ScalarMultiple(lDotN);
        //                 color = color.Add(diffuseContribution);
                        
        //                 // Specular light contribution
        //                 viewingVector = eye.Subtract(nearestObject.Model.Origin.Image(ray.PointAt(nearestHit))).Normalize();
        //                 halfVector = viewingVector.Add(lightVector).Normalize();
        //                 hDotN = halfVector.DotProduct(normal);
                        
        //                 if (hDotN > 0)
        //                 {
        //                     specularContribution = nearestObject.SpecularColor.TermMultiple(l.Color)
        //                         .ScalarMultiple(Math.Pow(hDotN, nearestObject.SpectularExponent));
        //                     color = color.Add(specularContribution);
        //                 }
        //             }
        //         }
                
        //         color.Clamp();
        //         return Color.FromArgb((int)color[0], (int)color[1], (int)color[2]);
        //     }		
        // }
    }
}