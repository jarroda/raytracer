using System.Drawing;
using MathNet.Numerics.LinearAlgebra;

namespace Tracer
{
    public class ViewportToWindowTransform : IViewportToWindowTransform
    {
        private Matrix<double> _m;
        
        public ViewportToWindowTransform(RectangleF w, RectangleF v)
        {
            var widthRatio = w.Width / v.Width;
            var heightRatio = w.Height / v.Height;

            _m = Matrix<double>.Build.DenseOfArray(new double[,] { 
                { widthRatio, 0, w.X - widthRatio * v.X },
                { 0,  -heightRatio, w.Y + heightRatio * v.Y },
                { 0, 0, 1 }
            });
        }

        public PointF Transform(PointF p1)
        {
            return new PointF(
                (float)(_m[0,0] * p1.X + _m[0,1] * p1.Y + _m[0,2]),
                (float)(_m[1,0] * p1.X + _m[1,1] * p1.Y + _m[1,2]));
        }
    }
}