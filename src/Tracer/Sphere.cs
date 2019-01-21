using System.Drawing;
using System.Linq;
using MathNet.Numerics.LinearAlgebra;

namespace Tracer
{
    public class Sphere : Traceable
    {
        public Sphere() { }
        public Sphere(Color baseColor, Color specular, double exponent)
        {
            BaseColor = baseColor.ToVector();
            SpecularColor = specular.ToVector();
            SpectularExponent = exponent;
        }

        public override double[] Intersections(Ray ray)
        {
            var localRay = GetLocalRay(ray);

            return Util.SolveQuadraticPositive(
                localRay.Direction.MagnitudeSquared(),
                2 * localRay.Origin.DotProduct(localRay.Direction),
                localRay.Origin.MagnitudeSquared() - 1
            );
        }

        public override Vector<double> GetNormalAt(Vector<double> p)
            => Model.TransformNormal(Model.InverseImage(p));

        public override Vector<double> GetBaseColorAt(Vector<double> p) => BaseColor;
    }
}