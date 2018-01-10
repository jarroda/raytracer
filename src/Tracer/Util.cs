using System;
using System.Collections.Generic;
using System.Numerics;

namespace Tracer
{    
    public static class Util
    {
        public static double nearlyZero = 0.5E-10;
        public static double nearlyInfinite = 0.5E10;
        
        public static void DumpEx(this Matrix4x4 m)
        {
            Console.WriteLine($"{m.M11}\t{m.M12}\t{m.M13}\t{m.M14}");
            Console.WriteLine($"{m.M21}\t{m.M22}\t{m.M23}\t{m.M24}");
            Console.WriteLine($"{m.M31}\t{m.M32}\t{m.M33}\t{m.M34}");
            Console.WriteLine($"{m.M41}\t{m.M42}\t{m.M43}\t{m.M44}");
            Console.WriteLine();
        }
        
        public static Vector3 Image(this Matrix4x4 m, Vector3 v)
        {
            return new Vector3(
                (m.M11 * v.X) + (m.M12 * v.Y) + (m.M13 * v.Z) + m.M14,
                (m.M21 * v.X) + (m.M22 * v.Y) + (m.M23 * v.Z) + m.M24,
                (m.M31 * v.X) + (m.M32 * v.Y) + (m.M33 * v.Z) + m.M34);
        }

        

        public static double[] SolveQuadraticPositive(double a, double b, double c)
        {
            var nz = nearlyZero;
            var ni = nearlyInfinite;
            // Linear case
            if (Math.Abs(a) < nearlyZero)
            {
                // No solutions
                if (Math.Abs(b) < nearlyZero)
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
                else if (d < nearlyZero)
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
    }
}