using MathNet.Numerics.LinearAlgebra;

namespace Tracer
{    
    public abstract class AmbientLight
    {
        public static Vector<double> Color { get; set; }
            = Vector.CreateVector3(0, 0, 0);
    }
}