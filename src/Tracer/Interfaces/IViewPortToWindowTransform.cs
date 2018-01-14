using System.Drawing;

namespace Tracer
{
    public interface IViewportToWindowTransform
    {
        PointF Transform(PointF viewportPoint);
    }
}