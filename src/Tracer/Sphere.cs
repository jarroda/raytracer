using System.Linq;
using System.Numerics;

namespace Tracer
{
    public class Sphere : Traceable
    {
        public Sphere() { }
        public Sphere(Vector3 baseColor, Vector3 specular, float exponent)
        {
            BaseColor = baseColor;
            SpecularColor = specular;
            SpectularExponent = exponent;
        }

        public override float[] Intersections(Ray ray)
            => IntersectionsD(ray).Cast<float>().ToArray();
            
        public double[] IntersectionsD(Ray ray)
        {
            var localRay = GetLocalRay(ray);

            return Util.SolveQuadraticPositive(
                localRay.Direction.LengthSquared(),
                2 * Vector3.Dot(localRay.Origin, localRay.Direction),
                localRay.Origin.LengthSquared() - 1
            );
        }

        public override Vector3 GetNormalAt(Vector3 p)
        {
            return Vector3.Zero;
        }

        public override Vector3 GetBaseColorAt(Vector3 p)
        {
            return BaseColor;
        }
    }
}