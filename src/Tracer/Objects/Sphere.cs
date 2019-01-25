using System.Drawing;
using System.Linq;
using System.Numerics;
using MathNet.Numerics.LinearAlgebra;

namespace Tracer.Objects
{
    public class Sphere : Traceable
    {
        public Sphere() { }
        public Sphere(Color baseColor, Color specular, double exponent)
        {
            BaseColor = baseColor;
            SpecularColor = specular;
            SpecularExponent = exponent;
        }

        public override float[] Intersections(Ray ray)
        {
            var localRay = GetLocalRay(ray);

            return Util.SolveQuadraticPositive(
                localRay.Direction.MagnitudeSquared(),
                2 * Vector3.Dot(localRay.Origin, localRay.Direction),
                localRay.Origin.MagnitudeSquared() - 1
            );
        }

        public override Vector3 GetNormalAt(Vector3 p)
            // => Model.TransformNormal(Model.InverseOld.Image(p.ToVector())).ToVector();
            => Model.TransformNormal(Model.InverseImage(p));

        public override Color GetBaseColorAt(Vector3 p) 
            => BaseColor;
    }
}