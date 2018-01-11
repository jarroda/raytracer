using MathNet.Numerics.LinearAlgebra;

namespace Tracer
{    
    public abstract class Light
    {
        public Vector<double> Color { get; protected set; }
            = Vector<double>.Build.DenseOfArray(new double[] { 0, 0, 0});

        public abstract Vector<double> GetLightVector(Vector<double> point);
    }
}