using System.Drawing;

namespace Tracer
{
    public interface IViewPortToWindowTransform
    {
        PointF Transform(PointF viewportPoint);
    }
}