using System;
using System.Collections.Generic;
using System.Drawing;
using System.Linq;
using System.Numerics;

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
        
        public static Color TraceARay(Ray ray, IEnumerable<Traceable> objects, IEnumerable<Light> lights, Vector3 eye, Color ambientLight)
            => ray.Trace(objects, lights, eye, ambientLight);

        /// <summary>
        /// Trace a ray and return the RGB color vector.
        /// </summary>
        /// <param name="ray">The current ray being traced.</param>
        /// <param name="objects">The collection of tracable objects.</param>
        /// <param name="lights">The collection of lights</param>
        /// <param name="eye">The eye vector</param>
        /// <returns>The RGB color vector3 if the ray hits an object, otherwise null.</returns>
        public static Color Trace(this Ray ray, IEnumerable<Traceable> objects, IEnumerable<Light> lights, Vector3 eye, Color ambientLight)
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

            float hit;
            float[] hits;
            float nearestHit = (float)Util.NearlyInfinite;
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
                return Color.Black;
            }
            else
            {
                var normal = nearestObject.GetNormalAt(ray.PointAt(nearestHit));
                var baseColor = nearestObject.GetBaseColorAt(ray.PointAt(nearestHit));
                var color = baseColor.ToVector3().TermMultiple(ambientLight.ToVector3());

                Vector3 lightVector,
                    viewingVector,
                    diffuseContribution, 
                    specularContribution,
                    halfVector;
                float lDotN, hDotN;
                
                foreach (var l in lights)
                {
                    lightVector = l.GetLightVector(ray.PointAt((float)nearestHit));
                    lDotN = Vector3.Dot(lightVector, normal);
                    
                    if (lDotN > 0)
                    {
                        // Diffuse light contribution
                        diffuseContribution = baseColor.ToVector3().TermMultiple(l.Color.ToVector3()).ScalarMultiple(lDotN);
                        color = color + diffuseContribution;
                        
                        // Specular light contribution
                        viewingVector = Vector3.Normalize(eye - nearestObject.Model.Origin.Image(ray.PointAt(nearestHit).ToVector()).ToVector());
                        halfVector = Vector3.Normalize(viewingVector + lightVector);
                        hDotN = Vector3.Dot(halfVector, normal);
                        
                        if (hDotN > 0)
                        {
                            specularContribution = nearestObject.SpecularColor.ToVector3().TermMultiple(l.Color.ToVector3())
                                .ScalarMultiple((float)Math.Pow(hDotN, nearestObject.SpecularExponent));
                            color = color + specularContribution;
                        }
                    }
                }
                
                return color.Clamp().ToColor();
            }
        }


        public static int ToRGB(this double val)
            => (int)Math.Floor(val >= 1 ? 255 : val * 256.0);
        
        public static int ToRGB(this float val)
            => (int)Math.Floor(val >= 1 ? 255 : val * 256.0);
        
        public static Color ToColor(this System.Numerics.Vector3 vector)
            => Color.FromArgb(vector.X.ToRGB(), vector.Y.ToRGB(), vector.Z.ToRGB());

        public static System.Numerics.Vector3 ToVector3(this Color color)
            => new System.Numerics.Vector3(
                (float)color.R / 256,
                (float)color.G / 256,
                (float)color.B / 256
            );

        // public static Vector<double> ToVector(this Color color)
        //     => Vector.CreateVector3(
        //         (double)color.R / 256,
        //         (double)color.G / 256,
        //         (double)color.B / 256
        //     );

        public static float[] SolveQuadraticPositive(float a, float b, float c)
        {
            // Linear case
            if (Math.Abs(a) < NearlyZero)
            {
                // No solutions
                if (Math.Abs(b) < NearlyZero)
                {
                    return new float[0];
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
                    return new float[0];
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
                    d = (float)Math.Sqrt(d);
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
        public static MathNet.Numerics.LinearAlgebra.Vector<double> ToVector(this System.Numerics.Vector3 vector)
            => Vector.CreateVector3(vector.X, vector.Y, vector.Z);

        public static Vector3 ToVector(this MathNet.Numerics.LinearAlgebra.Vector<double> vector)
            => new Vector3((float)vector[0], (float)vector[1], (float)vector[2]);
    }
}