using MathNet.Numerics.LinearAlgebra;

namespace Tracer
{    
    public abstract class AmbientLight
    {
        public Vector<double> Color { get; protected set; }
            = Vector<double>.Build.DenseOfArray(new double[] { 0, 0, 0});
    }
}