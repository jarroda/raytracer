using System.Collections.Generic;
using System.Drawing;
using System.Numerics;
using Tracer.Objects;

namespace Tracer
{
    public class Scene
    {
        public SizeF Viewport { get; set; }
        public RectangleF Window { get; set; }
        public double ViewingDistance { get; set; }
        public Vector3 Eye { get; set; }
        public Vector3 Center { get; set; } = new Vector3(0, 0, 0);
        public Vector3 Up { get; set; } = new Vector3(0, 1, 0);
        public Color AmbientLight { get; set; }
        public List<Light> Lights { get; set; }
        public List<Traceable> Objects { get; set; }
    }
}