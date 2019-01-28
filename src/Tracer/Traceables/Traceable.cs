using System.Drawing;
using System.Numerics;

namespace Tracer.Traceables
{
    public abstract class Traceable
    {
        public abstract bool Hit(Ray r, float tmin, float tmax, out HitRecord hit);
    }
}