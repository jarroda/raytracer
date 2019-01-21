using System;
using System.Numerics;

namespace Tracer
{    
    public class PointLight : Light
    {
        public Vector3 Location { get; set; }
        public override Vector3 GetLightVector(Vector3 point)
        {
            if (point == null)
            {
                throw new ArgumentNullException(nameof(point));
            }
            
            return Vector3.Normalize(Location - point);
        }
    }
}