using System;
using System.Drawing;
using System.Linq;
using System.Numerics;
using MathNet.Numerics.LinearAlgebra;

namespace Tracer.Objects
{
    public class Plane : Traceable
    {
        public Plane() { }
        public Plane(Color baseColor)
        {
            BaseColor = baseColor;
        }

        public override float[] Intersections(Ray ray)
        {
            var r = GetLocalRay(ray);

            if (Math.Abs(r.Direction.Y) <= Util.NearlyZero)
            {
                return new float[0];
            }

            var t = -r.Origin.Y / r.Direction.Y;

            // out of range

            return new float[] { t };
        }

        public override Vector3 GetNormalAt(Vector3 p)
            => new Vector3(0, 1, 0);
    }
}