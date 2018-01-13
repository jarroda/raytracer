using MathNet.Numerics.LinearAlgebra;

namespace Tracer
{    
    public abstract class Light
    {
        public Vector<double> Color { get; set; }
            = Vector.CreateVector3(0, 0, 0);

        public abstract Vector<double> GetLightVector(Vector<double> point);
    }
}