using System.Collections.Generic;
using System.Drawing;
using MathNet.Numerics.LinearAlgebra;

namespace Tracer
{
    public class Scene
    {
        public SizeF Viewport { get; set; }
        public RectangleF Window { get; set; }
        public double ViewingDistance { get; set; }
        public Vector<double> Eye { get; set; }
        public Vector<double> Center { get; set; } = Vector.CreateVector3(0, 0, 0);
        public Vector<double> Up { get; set; } = Vector.CreateVector3(0, 1, 0);
        public Vector<double> AmbientLight { get; set; }
        public List<Light> Lights { get; set; }
        public List<Traceable> Objects { get; set; }
    }
}