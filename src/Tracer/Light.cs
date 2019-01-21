using System.Drawing;
using System.Numerics;

namespace Tracer
{    
    public abstract class Light
    {
        public Color Color { get; set; } = Color.White;

        public abstract Vector3 GetLightVector(Vector3 point);
    }
}