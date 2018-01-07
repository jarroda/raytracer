using System.Linq;
using System.Numerics;

namespace Tracer
{
    public class Sphere : Traceable
    {        
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
    }
}