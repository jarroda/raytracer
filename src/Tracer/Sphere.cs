using System.Linq;
using MathNet.Numerics.LinearAlgebra;

namespace Tracer
{
    public class Sphere : Traceable
    {
        public Sphere() { }
        public Sphere(Vector<double> baseColor, Vector<double> specular, double exponent)
        {
            BaseColor = baseColor;
            SpecularColor = specular;
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