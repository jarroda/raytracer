using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using MathNet.Numerics.LinearAlgebra;

namespace Tracer
{    
    public static class Util
    {
        public const double NearlyZero = 0.5E-10;
        public const double NearlyInfinite = 0.5E10;
        
        // public static void DumpEx(this Matrix4x4 m)
        // {
        //     Console.WriteLine($"{m.M11}\t{m.M12}\t{m.M13}\t{m.M14}");
        //     Console.WriteLine($"{m.M21}\t{m.M22}\t{m.M23}\t{m.M24}");
        //     Console.WriteLine($"{m.M31}\t{m.M32}\t{m.M33}\t{m.M34}");
        //     Console.WriteLine($"{m.M41}\t{m.M42}\t{m.M43}\t{m.M44}");
        //     Console.WriteLine();
        // }
        
        public static Color TraceARay(Ray ray, IEnumerable<Traceable> objects, IEnumerable<Light> lights, Vector<double> eye)
        {
            var color = ray.Trace(objects, lights, eye);
            return color == null ? Color.Black :
                Color.FromArgb(color[0].ToRGB(), color[1].ToRGB(), color[2].ToRGB());
        }

        /// <summary>
        /// Trace a ray and return the RGB color vector.
        /// </summary>
        /// <param name="ray">The current ray being traced.</param>
        /// <param name="objects">The collection of tracable objects.</param>
        /// <param name="lights">The collection of lights</param>
        /// <param name="eye">The eye vector</param>
        /// <returns>The RGB color vector3 if the ray hits an object, otherwise null.</returns>
        public static Vector<double> Trace(this Ray ray, IEnumerable<Traceable> objects, IEnumerable<Light> lights, Vector<double> eye)
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
                return null;
            }
            else
            {
                var normal = nearestObject.GetNormalAt(ray.PointAt((float)nearestHit).ToVector());
                var baseColor = nearestObject.GetBaseColorAt(ray.PointAt((float)nearestHit).ToVector());
                var color = baseColor.TermMultiple(AmbientLight.Color);
                
                Vector<double> lightVector,	
                    diffuseContribution, 
                    specularContribution,
                    viewingVector,
                    halfVector;
                double lDotN, hDotN;
                
                foreach (var l in lights)
                {
                    lightVector = l.GetLightVector(ray.PointAt((float)nearestHit)).ToVector();
                    lDotN = lightVector.DotProduct(normal);
                    
                    if (lDotN > 0)
                    {
                        // Diffuse light contribution
                        diffuseContribution = baseColor.TermMultiple(l.Color.ToVector()).ScalarMultiple(lDotN);
                        color = color.Add(diffuseContribution);
                        
                        // Specular light contribution
                        viewingVector = eye.Subtract(nearestObject.Model.Origin.Image(ray.PointAt((float)nearestHit).ToVector())).Normalize();
                        halfVector = viewingVector.Add(lightVector).Normalize();
                        hDotN = halfVector.DotProduct(normal);
                        
                        if (hDotN > 0)
                        {
                            specularContribution = nearestObject.SpecularColor.TermMultiple(l.Color.ToVector())
                                .ScalarMultiple(Math.Pow(hDotN, nearestObject.SpectularExponent));
                            color = color.Add(specularContribution);
                        }
                    }
                }
                
                color.Clamp();
                return color;
            }
        }


        public static int ToRGB(this double val)
            => (int)Math.Floor(val >= 1 ? 255 : val * 256.0);
        
        public static int ToRGB(this float val)
            => (int)Math.Floor(val >= 1 ? 255 : val * 256.0);

        public static System.Numerics.Vector3 ToVector3(this Color color)
            => new System.Numerics.Vector3(
                (float)color.R / 256,
                (float)color.G / 256,
                (float)color.B / 256
            );

        public static Vector<double> ToVector(this Color color)
            => Vector.CreateVector3(
                (double)color.R / 256,
                (double)color.G / 256,
                (double)color.B / 256
            );

        public static double[] SolveQuadraticPositive(double a, double b, double c)
        {
            // var nz = NearlyZero;
            // var ni = NearlyInfinite;
            // Linear case
            if (Math.Abs(a) < NearlyZero)
            {
                // No solutions
                if (Math.Abs(b) < NearlyZero)
                {
                    return new double[0];
                }
                // One linear solution
                else
                {
                    var x1 = -c / b;
                    return new[] { x1 };
                }
            }
            // The quadratic case
            else
            {
                // d = discriminant
                var d = b * b - 4 * a * c;

                // Solutions are complex
                if (d < 0)
                {
                    return new double[0];
                }
                else if (d < NearlyZero)
                {
                    // One double root
                    var x1 = -b / (2 * a);
                    return new[] { x1 };
                }
                else
                {
                    // Two real roots
                    d = Math.Sqrt(d);
                    var x1 = (-b + d) / (2 * a);
                    var x2 = (-b - d) / (2 * a);
                    if (x1 > x2)
                    {
                        var t = x1;
                        x1 = x2;
                        x2 = t;
                    }
                    return new[] { x1, x2 };
                }
            }
        }
        public static Vector<double> ToVector(this System.Numerics.Vector3 vector)
            => Vector.CreateVector3(vector.X, vector.Y, vector.Z);

        public static System.Numerics.Vector3 ToVector(this Vector<double> vector)
            => new System.Numerics.Vector3((float)vector[0], (float)vector[1], (float)vector[2]);
    }
}