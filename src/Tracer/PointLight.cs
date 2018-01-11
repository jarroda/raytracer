using System;
using MathNet.Numerics.LinearAlgebra;

namespace Tracer
{    
    public class PointLight
    {
        public Vector<double> Location { get; set; }
        public Vector<double> GetLightVector(Vector<double> point)
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