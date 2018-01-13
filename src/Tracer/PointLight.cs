using System;
using MathNet.Numerics.LinearAlgebra;

namespace Tracer
{    
    public class PointLight : Light
    {
        public Vector<double> Location { get; set; }
        public override Vector<double> GetLightVector(Vector<double> point)
        {
            if (point == null)
            {
                throw new ArgumentNullException(nameof(point));
            }
            
            // TODO: Make this not brittle (ie, not return null)
            return Location?.Subtract(point).Normalize();
        }
    }
}